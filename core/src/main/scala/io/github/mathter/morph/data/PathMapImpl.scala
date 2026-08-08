package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path

import java.util
import scala.collection.mutable

private sealed abstract class AbstractPathMap(val map: InnerMap = new InnerMap) extends PathMap with Serializable {
  override def apply[T](path: Path): Opt[T] = {
    val paths = path.expand.map(_.local)
    val valuesMapList: List[InnerMap] = if (paths.length > 1) {
      paths
        .take(if (paths.length > 1) paths.length - 1 else 1)
        .foldLeft(List(this.map))((l: List[InnerMap], r) => getSubMaps(l, r))
    } else {
      List(this.map)
    }
    val values = valuesMapList
      .flatMap(innerMap => innerMap.getOrElse(paths.last, Opt.empty))
      .map(reverseTranslate)

    values.length match {
      case 0 => Opt.empty[T]
      case 1 => Opt(values.head.asInstanceOf[T])
      case _ => Opt(this.translateList(values).asInstanceOf[T])
    }
  }

  override def iget[T](path: Path): Opt[T] = {
    val paths = path.expand.map(_.local)
    val list: List[InnerMap] = if (paths.length > 1) {
      paths
        .foldLeft(List(this.map))((l: List[InnerMap], r) => getSubMaps(l, r))
    } else {
      List(this.map)
    }

    list.length match {
      case 0 => Opt.empty[T]
      case 1 => Opt(list.flatMap(_.values.flatten).asInstanceOf[T])
      case x => throw MoreThenOneItemException("There are %s item by path '%s'!".formatted(x, path))
    }
  }

  def getSubMaps(list: List[InnerMap], path: Path): List[InnerMap] = {
    list.flatMap(innerMap => innerMap.get(path)
      .map(e => e.filter(e => e != null && e.isInstanceOf[InnerMap])
        .map(_.asInstanceOf[InnerMap])).getOrElse(List()))
  }

  override def update[T](path: Path, value: T): Unit = {
    var tmp = this.map
    val paths = path.expand.map(_.local)

    for (i <- 0 until (paths.length - 1)) {
      val element = tmp.getOrElse(paths(i), null)

      if (element == null) {
        val newMap = new InnerMap
        val newQueue = InnerMap.newQueue :+ newMap
        tmp.put(paths(i), newQueue)
        tmp = newMap
      } else {
        tmp = element
          .filter(_.isInstanceOf[InnerMap])
          .map(_.asInstanceOf[InnerMap])
          .head
      }
    }

    import scala.jdk.CollectionConverters.*

    this.translate(value) match {
      case x: List[?] => tmp.getOrElseUpdate(paths.last, InnerMap.newQueue).addAll(x)
      case x: util.List[?] => tmp.getOrElseUpdate(paths.last, InnerMap.newQueue).addAll(x.asScala)
      case x => tmp.getOrElseUpdate(paths.last, InnerMap.newQueue).addOne(x)
    }
  }

  override def keys: Set[Path] = this.map.keySet.toSet

  override def entries: List[(Path, ?)] = {
    this.map.keySet
      .map(key =>
        (
          key, {
          val values = this.map(key).map(value => this.reverseTranslate(value))
          values.length match {
            case 0 =>
            case 1 => values.head
            case _ => values.toList
          }
        }
        )
      )
      .toList
  }

  override def toMap[K](f: Path => K = p => p.segment): collection.Map[K, Any] = this.flat(this)(using f)

  override def toJavaMap[K](f: Path => K): util.Map[K, Object] = this.flatAsJava(this)(using f)

  protected def flat[K](pathMap: PathMap)(using f: Path => K): collection.Map[K, Any] = {
    pathMap.entries
      .map(t => {
        (
          f(t._1),
          t._2 match {
            case pm: PathMap => this.flat(pm)
            case list: List[?] => list.map {
              case pm: PathMap => this.flat(pm)
              case e => e
            }
            case _ => t._2
          }
        )
      })
      .foldLeft(mutable.Map.empty)((m, t) => {
        m.put(t._1, t._2.asInstanceOf[Object])
        m
      })
  }

  protected def flatAsJava[K](pathMap: PathMap)(using f: Path => K): util.Map[K, Object] = {
    import scala.jdk.CollectionConverters.*

    pathMap.entries
      .map(t => {
        (
          f(t._1),
          t._2 match {
            case pm: PathMap => this.flatAsJava(pm)
            case list: List[?] => list.map {
              case pm: PathMap => this.flatAsJava(pm)
              case e => e
            }.asJavaCollection.asInstanceOf[Object]
            case _ => t._2
          }
        )
      })
      .foldLeft(new util.HashMap)((m, t) => {
        m.put(t._1, t._2.asInstanceOf[Object])
        m
      })
  }

  protected def translate(value: Any): Any = {
    value match {
      case map: AbstractPathMap => map.map
      case _ => value
    }
  }

  protected def reverseTranslate(value: Any): Any

  protected def translateList[E](x: List[E]): Any
}

private class EPathMap(map: InnerMap = new InnerMap) extends AbstractPathMap(map) {
  protected def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new EPathMap(map)
      case _ => value
    }
  }

  override def asJava: JPathMap = new JEPathMap(this.map)

  override def asScala: PathMap = this

  override inline protected def translateList[E](x: List[E]): Any = x
}

private class JEPathMap(map: InnerMap = new InnerMap) extends AbstractPathMap(map) with JPathMap {
  protected def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new EPathMap(map)
      case _ => value
    }
  }

  override def asJava: JPathMap = this

  override def asScala: PathMap = new EPathMap(this.map)

  override inline protected def translateList[E](x: List[E]): Any = {
    import scala.jdk.CollectionConverters.*

    x.asJava
  }
}