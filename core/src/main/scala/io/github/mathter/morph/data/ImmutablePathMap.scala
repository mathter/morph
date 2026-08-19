package io.github.mathter.morph.data

import java.util

/**
 * A read-only variant of [[PathMap]].
 *
 * `ImmutablePathMap` shares the same storage and retrieval semantics as the
 * mutable `PathMap` implementations but exposes only read operations. Any
 * attempt to modify the map ([[update]] or [[put]]) throws an
 * `UnsupportedOperationException`.
 *
 * Contract details:
 *  - Reads: `apply`, `get`, `iget`, `keys`, `entries`, `toMap` and related
 *    methods behave exactly like the mutable implementations.
 *  - Nested values: every nested `PathMap` returned by a read operation is
 *    itself an `ImmutablePathMap`, so read-only semantics extend recursively.
 *  - Backing semantics: the map is backed by the same underlying data as the
 *    map it was created from (see [[ImmutablePathMap.from]]), so mutations
 *    performed through a mutable view of the same data remain visible.
 *  - Views: [[asJava]] and [[asScala]] produce read-only views of the same
 *    underlying data.
 *  - Mutability: `update` and `put` throw `UnsupportedOperationException`.
 *
 * Use [[PathMap.asImmutable]] or [[ImmutablePathMap.from]] to obtain an
 * immutable view of an existing `PathMap`, or [[ImmutablePathMap.empty]] to
 * create an empty immutable map.
 */
trait ImmutablePathMap extends PathMap

object ImmutablePathMap {
  /**
   * Creates an empty `ImmutablePathMap` with Scala collection semantics.
   *
   * @return an empty, read-only `ImmutablePathMap`
   *
   * @example
   * {{{
   * val pathMap = ImmutablePathMap.empty
   * }}}
   */
  def empty: ImmutablePathMap = new ImmutableEPathMap()

  /**
   * Wraps the given `PathMap` into a read-only `ImmutablePathMap`.
   *
   * If the provided map is already an `ImmutablePathMap`, it is returned as
   * is. Otherwise an immutable view backed by the same underlying data is
   * created: mutations performed through the original map remain visible
   * through the returned view.
   *
   * @param pathMap the map to wrap
   * @return a read-only view of `pathMap`
   *
   * @example
   * {{{
   * val source = PathMap.empty
   * source("key") = "value"
   * val readOnly = ImmutablePathMap.from(source)
   * }}}
   */
  def from(pathMap: PathMap): ImmutablePathMap = {
    pathMap match {
      case map: ImmutablePathMap => map
      case map: AbstractPathMap => new ImmutableEPathMap(map.map)
      case _ => new ImmutableEPathMap(toInnerMap(pathMap))
    }
  }

  /**
   * Creates an empty `ImmutableJPathMap` with Java collection semantics.
   *
   * @return an empty, read-only `ImmutableJPathMap`
   */
  def jempty: ImmutableJPathMap = new ImmutableJEPathMap()

  /**
   * Wraps the given `JPathMap` into a read-only `ImmutableJPathMap`.
   *
   * If the provided map is already an `ImmutableJPathMap`, it is returned as
   * is. Otherwise an immutable view backed by the same underlying data is
   * created.
   *
   * @param pathMap the map to wrap
   * @return a read-only view of `pathMap`
   */
  def jfrom(pathMap: JPathMap): ImmutableJPathMap = {
    pathMap match {
      case map: ImmutableJPathMap => map
      case map: AbstractPathMap => new ImmutableJEPathMap(map.map)
      case _ => new ImmutableJEPathMap(toInnerMap(pathMap))
    }
  }

  private def toInnerMap(pathMap: PathMap): InnerMap = {
    import scala.jdk.CollectionConverters.*

    val map = new InnerMap(0)

    pathMap.entries.foreach { case (path, value) =>
      value match {
        case nested: PathMap => map.getOrElseUpdate(path, InnerMap.newQueue).addOne(toInnerMap(nested))
        case list: List[?] => list.foreach {
          case nested: PathMap => map.getOrElseUpdate(path, InnerMap.newQueue).addOne(toInnerMap(nested))
          case element => map.getOrElseUpdate(path, InnerMap.newQueue).addOne(element)
        }
        case list: util.List[?] => list.asScala.foreach {
          case nested: PathMap => map.getOrElseUpdate(path, InnerMap.newQueue).addOne(toInnerMap(nested))
          case element => map.getOrElseUpdate(path, InnerMap.newQueue).addOne(element)
        }
        case other => map.getOrElseUpdate(path, InnerMap.newQueue).addOne(other)
      }
    }

    map
  }
}

/**
 * Java-friendly read-only variant of [[JPathMap]].
 *
 * An `ImmutableJPathMap` is an [[ImmutablePathMap]] intended for Java
 * interoperability: list values are exposed as `java.util.List` instances.
 * `update` and `put` throw `UnsupportedOperationException`.
 *
 * @see [[ImmutablePathMap]] for the contract details
 */
trait ImmutableJPathMap extends JPathMap with ImmutablePathMap

object ImmutableJPathMap {
  /**
   * Creates an empty `ImmutableJPathMap` with Java collection semantics.
   *
   * @return an empty, read-only `ImmutableJPathMap`
   */
  def empty: ImmutableJPathMap = ImmutablePathMap.jempty

  /**
   * Wraps the given `JPathMap` into a read-only `ImmutableJPathMap`.
   *
   * @param pathMap the map to wrap
   * @return a read-only view of `pathMap`
   */
  def from(pathMap: JPathMap): ImmutableJPathMap = ImmutablePathMap.jfrom(pathMap)
}