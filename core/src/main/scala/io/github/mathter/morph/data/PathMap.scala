package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path
import org.apache.commons.lang3.tuple.Pair

import java.util
import scala.jdk.CollectionConverters.*

/**
 * A hierarchical map that stores values at paths, similar to accessing nested objects in JSON or XPath.
 *
 * `PathMap` provides a tree-like data structure where keys are `Path` objects representing nested locations.
 * Values can be stored at any path, and retrieving values can expand multiple paths if wildcards are used.
 *
 * This trait serves as the core interface for path-based value storage. It supports:
 *  - Path-based get/set operations
 *  - Multiple retrieval strategies (normal and inclusive get)
 *  - Conversion to standard Scala and Java collections
 *  - Interoperability with Java collections
 *  - Hierarchical value storage and retrieval
 *
 * @example
 * {{{
 * val pathMap = PathMap.empty
 * pathMap("title") = "My Book"
 *
 * val author = PathMap.empty
 * author("name") = "John Doe"
 * author("lastName") = "Doe"
 * pathMap("authors") = author
 *
 * val title: Opt[String] = pathMap("title")  // Some("My Book")
 * val author: Opt[PathMap] = pathMap("authors")  // Some(PathMap with name and lastName)
 * }}}
 *
 * @see [[Path]] for information about path expressions
 */
trait PathMap {
  /**
   * Retrieves a value at the specified path.
   *
   * Supports path expansion with wildcards. If the path expands to multiple values,
   * they are combined into a list.
   *
   * @param path the path to retrieve
   * @tparam T the expected type of the value
   * @return an `Opt` containing the value if found, `None` otherwise
   * @example
   * {{{
   * val value: Opt[String] = pathMap("authors/name")
   * }}}
   */
  def apply[T](path: Path): Opt[T]

  /**
   * Retrieves a value at the specified path.
   *
   * Alias for [[apply]]. Supports path expansion with wildcards.
   *
   * @param path the path to retrieve
   * @tparam T the expected type of the value
   * @return an `Opt` containing the value if found, `None` otherwise
   */
  def get[T](path: Path): Opt[T] = this.apply(path)

  /**
   * Inclusively retrieves a value at the specified path without path expansion.
   *
   * Unlike [[apply]], this method retrieves all values at paths that end with the specified segment,
   * treating it as a predicate rather than expanding the full path.
   *
   * @param path the path segment to retrieve
   * @tparam T the expected type of the value
   * @return an `Opt` containing the value if found, `None` otherwise
   * @throws MoreThenOneItemException if the path matches more than one item
   */
  def iget[T](path: Path): Opt[T]

  /**
   * Updates a value at the specified path.
   *
   * Alias for [[update]]. Creates intermediate paths if they don't exist.
   *
   * @param path  the path where to store the value
   * @param value the value to store
   * @tparam T the type of the value
   * @example
   * {{{
   * pathMap("authors/name") = "John Doe"
   * }}}
   */
  def put[T](path: Path, value: T): Unit = update(path, value)

  /**
   * Updates or creates a value at the specified path.
   *
   * Creates any intermediate paths that don't exist. If a list value is stored at the path,
   * multiple values can exist at the same path.
   *
   * @param path  the path where to store the value
   * @param value the value to store (can be a PathMap, list, or scalar value)
   * @tparam T the type of the value
   */
  def update[T](path: Path, value: T): Unit

  /**
   * Returns all keys (top-level paths) in this `PathMap`.
   *
   * @return a set of all paths stored in this map
   * @example
   * {{{
   * val keys: Set[Path] = pathMap.keys  // Set("title", "authors", "isbn")
   * }}}
   */
  def keys: Set[Path]

  /**
   * Returns all keys (top-level paths) in this `PathMap` as a Java `Set`.
   *
   * Convenience method for Java interoperability.
   *
   * @return a Java `Set` of all paths stored in this map
   */
  def jkeys: util.Set[Path] = this.keys.asJava

  /**
   * Returns `true` if this `PathMap` contains no entries.
   *
   * @return `true` if this map is empty, `false` otherwise
   */
  def isEmpty: Boolean = this.keys.isEmpty

  /**
   * Returns `true` if this `PathMap` contains at least one entry.
   *
   * @return `true` if this map is non-empty, `false` otherwise
   */
  def nonEmpty: Boolean = !this.isEmpty

  /**
   * Returns all key-value entries in this `PathMap`.
   *
   * @return a list of tuples where the first element is the path and the second is the value
   * @example
   * {{{
   * val entries: List[(Path, ?)] = pathMap.entries
   * }}}
   */
  def entries: List[(Path, ?)]

  /**
   * Returns all key-value entries in this `PathMap` as a Java `List` of `Pair`s.
   *
   * Convenience method for Java interoperability.
   *
   * @return a Java `List` of `Pair` entries
   */
  def jentries: util.List[Pair[Path, ?]] = this.entries.map(e => Pair.of(e._1, e._2)).asJava

  /**
   * Flattens the hierarchical structure into a single-level map.
   *
   * Recursively flattens nested `PathMap` instances. The keys of the resulting map are
   * determined by the provided function.
   *
   * @param f a function that transforms `Path` objects into keys
   * @tparam K the type of the resulting keys
   * @return a flattened Scala `Map` with transformed keys
   * @example
   * {{{
   * val flatMap = pathMap.toMap(_.segment)  // Keys become path segments
   * }}}
   */
  def toMap[K](f: Path => K): collection.Map[K, Any]

  /**
   * Flattens the hierarchical structure into a single-level Java map.
   *
   * Recursively flattens nested `PathMap` instances and converts the result to a Java `Map`.
   *
   * @param f a function that transforms `Path` objects into keys
   * @tparam K the type of the resulting keys
   * @return a flattened Java `Map` with transformed keys
   */
  def toJavaMap[K](f: Path => K): util.Map[K, Object]

  /**
   * Converts this `PathMap` to a Java-compatible `JPathMap`.
   *
   * @return a `JPathMap` view of this map, compatible with Java collections
   */
  def asJava: JPathMap

  /**
   * Converts this `PathMap` to a Scala-compatible `PathMap`.
   *
   * If already Scala-compatible, returns itself.
   *
   * @return this `PathMap` or a Scala-compatible equivalent
   */
  def asScala: PathMap

  /**
   * Returns a read-only view of this `PathMap`.
   *
   * The returned `ImmutablePathMap` is backed by the same underlying data as
   * this map: `update` and `put` throw an `UnsupportedOperationException`,
   * and every nested `PathMap` returned by a read operation is itself an
   * `ImmutablePathMap`.
   *
   * @return an immutable view of this map
   * @example
   * {{{
   * val readOnly = pathMap.asImmutable
   * }}}
   */
  def asImmutable: ImmutablePathMap = ImmutablePathMap.from(this)

  /**
   * Returns a deep copy of this `PathMap`.
   *
   * The returned `PathMap` shares no mutable state with this map: subsequent
   * updates to either map do not affect the other, including nested structures.
   * Note that stored values themselves are not cloned; they are shared by
   * reference between the original and the copy.
   *
   * @return a deep copy of this map
   * @example
   * {{{
   * val copy = pathMap.copy
   * }}}
   */
  def copy: PathMap
}

object PathMap {
  /**
   * Creates an empty `PathMap` with Scala collection semantics.
   *
   * @return an empty, mutable `PathMap`
   * @example
   * {{{
   * val pathMap = PathMap.empty
   * pathMap("key") = "value"
   * }}}
   */
  def empty: PathMap = {
    EPathMap()
  }

  /**
   * Creates an empty `PathMap` with Java collection semantics.
   *
   * @return an empty, mutable `JPathMap`
   */
  def jempty: JPathMap = {
    JEPathMap()
  }
}