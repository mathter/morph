// Copyright (c) 2026 mathter
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, without
// warranties or condition of any kind. You may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// All rights reserved.
package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path

import java.util
import scala.collection.mutable

/**
 * Internal base implementation of [[PathMap]] backed by an [[InnerMap]].
 *
 * This abstract class provides the shared logic for storing, retrieving and
 * traversing hierarchical path-based data. It is not part of the public API
 * surface and is marked `private` to the package implementation.
 *
 * Responsibilities:
 *  - Maintain the mutable backing [[InnerMap]] instance
 *  - Implement common retrieval semantics used by both Scala and Java views
 *    (e.g. [[apply]] and [[iget]])
 *  - Provide generic flattening helpers (`flat` / `flatAsJava`) used by
 *    concrete subclasses to produce Scala and Java maps respectively
 *  - Define translation hooks ([[reverseTranslate]] and [[translateList]])
 *    that convert between raw backing values and higher-level [[PathMap]]
 *    and collection types. Concrete subclasses must implement these hooks to
 *    control whether lists are exposed as Scala `List` or Java `List`.
 *
 * Note: implementations assume `InnerMap` stores collections of values for
 * each path segment and that nested `InnerMap` instances represent nested
 * PathMap structures.
 */
private sealed abstract class AbstractPathMap(val map: InnerMap = new InnerMap(0)) extends PathMap with Serializable {
  override def apply[T](path: Path): Opt[T] = {
    val paths = path.expand.map(_.local)
    val valuesMapList: List[InnerMap] = paths
      .take(paths.length - 1)
      .foldLeft(List(this.map))((l: List[InnerMap], r) => getSubMaps(l, r))
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
    val list: List[InnerMap] = paths
      .foldLeft(List(this.map))((l: List[InnerMap], r) => getSubMaps(l, r))

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
        val newMap = new InnerMap(this.map.id)
        val newQueue = InnerMap.newQueue :+ newMap
        tmp.put(paths(i), newQueue)
        tmp = newMap
      } else {
        tmp = element
          .filter(_.isInstanceOf[InnerMap])
          .map(_.asInstanceOf[InnerMap])
          .head

        if (this.map.id != tmp.id) {
          val index = element.indexOf(tmp)
          val newMap = AbstractPathMap.copy(tmp, this.map.id)
          element.drop(index)
          element(index) = newMap
          tmp = newMap
        }
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

  override def remove(path: Path): Unit = {
    val paths = path.expand.map(_.local)
    val valuesMapList: List[InnerMap] = paths
      .take(paths.length - 1)
      .foldLeft(List(this.map))((l: List[InnerMap], r) => getSubMaps(l, r))
    valuesMapList.foreach(innerMap => innerMap.remove(paths.last))
  }

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

/**
 * Scala-oriented `PathMap` implementation.
 *
 * This implementation exposes list values as Scala `List` instances and is the
 * natural view for Scala callers. Conversions to Java views are provided via
 * [[asJava]] which produces a [[JEPathMap]] backed by the same underlying
 * `InnerMap`.
 *
 * @param map the backing [[InnerMap]] instance (shared by views produced by
 *            asJava/asScala when appropriate)
 */
private class EPathMap(map: InnerMap = new InnerMap(0)) extends AbstractPathMap(map) {
  protected def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new EPathMap(map)
      case _ => value
    }
  }

  override def asJava: JPathMap = new JEPathMap(this.map)

  override def asScala: PathMap = this

  override inline protected def translateList[E](x: List[E]): Any = x

  override def copy: PathMap = new EPathMap(AbstractPathMap.copy(this.map, this.map.id + 1))
}

private object AbstractPathMap {
  infix def copy(x: InnerMap, id: Int): InnerMap = {
    val tmp = new InnerMap(id)
    x.foreach {
      (p, v) =>
        tmp(p) = v match {
          case x: mutable.Queue[Any] => InnerMap.newQueue.addAll(x)
          case x => x
        }
    }
    tmp
  }
}

/**
 * Java-oriented `PathMap` implementation (JPathMap).
 *
 * This implementation presents list values as `java.util.List` (see
 * [[translateList]] behavior) so Java callers get the familiar collection
 * types. `asScala` returns an [[EPathMap]] view backed by the same
 * `InnerMap` so conversions between views are cheap and reflect the same
 * underlying data (i.e. they are backed, not defensive copies).
 *
 * @param map the backing [[InnerMap]] instance (shared by views produced by
 *            asJava/asScala when appropriate)
 */
private class JEPathMap(map: InnerMap = new InnerMap(0)) extends AbstractPathMap(map) with JPathMap {
  protected def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new EPathMap(map)
      case _ => value
    }
  }

  override def asJava: JPathMap = this

  override def asScala: PathMap = new EPathMap(this.map)

  override def asImmutable: ImmutablePathMap = new ImmutableJEPathMap(this.map)

  override inline protected def translateList[E](x: List[E]): Any = {
    import scala.jdk.CollectionConverters.*

    x.asJava
  }

  override def copy: PathMap = new JEPathMap(AbstractPathMap.copy(this.map, this.map.id + 1))
}

/**
 * Read-only Scala-oriented `PathMap` implementation.
 *
 * This implementation shares the storage and retrieval semantics of
 * [[AbstractPathMap]] but is read-only: [[update]] and [[put]] throw an
 * `UnsupportedOperationException`. Every nested `PathMap` returned by a read
 * operation is itself an [[ImmutableEPathMap]], so read-only semantics extend
 * recursively to nested values and list elements.
 *
 * @param map the backing [[InnerMap]] instance (shared by views produced by
 *            asJava/asScala when appropriate)
 */
private class ImmutableEPathMap(map: InnerMap = new InnerMap(0)) extends AbstractPathMap(map) with ImmutablePathMap {
  protected def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new ImmutableEPathMap(map)
      case map: PathMap => ImmutablePathMap.from(map)
      case _ => value
    }
  }

  override def update[T](path: Path, value: T): Unit = {
    throw new UnsupportedOperationException("ImmutablePathMap does not support update operation")
  }

  override def remove(path: Path): Unit = {
    throw new UnsupportedOperationException("ImmutablePathMap does not support remove operation")
  }

  override def asJava: JPathMap = new ImmutableJEPathMap(this.map)

  override def asScala: PathMap = this

  override inline protected def translateList[E](x: List[E]): Any = x

  override def copy: PathMap = new EPathMap(AbstractPathMap.copy(this.map, this.map.id + 1))
}

/**
 * Read-only Java-oriented `PathMap` implementation (ImmutableJPathMap).
 *
 * This implementation presents list values as `java.util.List` instances so
 * Java callers get the familiar collection types. It is read-only: [[update]]
 * and [[put]] throw an `UnsupportedOperationException`.
 *
 * @param map the backing [[InnerMap]] instance (shared by views produced by
 *            asJava/asScala when appropriate)
 */
private class ImmutableJEPathMap(map: InnerMap = new InnerMap(0)) extends AbstractPathMap(map) with ImmutableJPathMap {
  protected def reverseTranslate(value: Any): Any = {
    value match {
      case map: InnerMap => new ImmutableEPathMap(map)
      case map: PathMap => ImmutablePathMap.from(map)
      case _ => value
    }
  }

  override def update[T](path: Path, value: T): Unit = {
    throw new UnsupportedOperationException("ImmutableJPathMap does not support update operation")
  }

  override def remove(path: Path): Unit = {
    throw new UnsupportedOperationException("ImmutableJPathMap does not support remove operation")
  }

  override def asJava: JPathMap = this

  override def asScala: PathMap = new ImmutableEPathMap(this.map)

  override inline protected def translateList[E](x: List[E]): Any = {
    import scala.jdk.CollectionConverters.*

    x.asJava
  }

  override def copy: JPathMap = new JEPathMap(AbstractPathMap.copy(this.map, this.map.id + 1))
}