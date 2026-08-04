package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path

import java.util
import scala.collection
import scala.collection.generic.DefaultSerializationProxy
import scala.collection.{Factory, mutable}

private class EPathMap(private val map: InnerMap = new InnerMap) extends PathMap with Serializable {
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
      case _ => Opt(values.asInstanceOf[T])
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
        val newQueue = EPathMap.newQueue :+ newMap
        tmp.put(paths(i), newQueue)
        tmp = newMap
      } else {
        tmp = element
          .filter(_.isInstanceOf[InnerMap])
          .map(_.asInstanceOf[InnerMap])
          .head
      }
    }

    tmp.getOrElseUpdate(paths.last, EPathMap.newQueue).addOne(this.translate(value))
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

  private def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new EPathMap(map)
      case _ => value
    }
  }

  private def translate(value: Any): Any = {
    value match {
      case map: EPathMap => map.map
      case _ => value
    }
  }
}

private object EPathMap {
  private def newQueue: mutable.Queue[Any] = mutable.Queue.empty
}

class InnerMap extends mutable.LinkedHashMap[Path, mutable.Queue[Any]] with Serializable {
  protected override def writeReplace(): AnyRef = new DefaultSerializationProxy(new DeserializationFactory, this)

  private class DeserializationFactory extends Factory[(Path, mutable.Queue[Any]), InnerMap] with Serializable {
    override def fromSpecific(it: IterableOnce[(Path, mutable.Queue[Any])]): InnerMap = new InnerMap().addAll(it)

    override def newBuilder: mutable.Builder[(Path, mutable.Queue[Any]), InnerMap] =
      new mutable.GrowableBuilder(new InnerMap())
  }
}