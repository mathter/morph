package io.github.mathter.morph.dsl

import io.github.mathter.morph.dsl.Source

/**
 * Fluent helpers for `Source[List[T]]` instances providing idiomatic list
 * operations as extension methods.
 */
implicit class ListSourceOps[T](val x: Source[List[T]]) {
  /** First element of the list source. */
  inline def first: Source[T] = x.dsl.first(x)

  /** Last element of the list source. */
  inline def last: Source[T] = x.dsl.last(x)

  /** Element at the index provided by the given source. */
  infix inline def index(source: Source[Int]): Source[T] = x.dsl.index(x, source)

  /** Map elements using a plain function. */
  infix inline def mapElem[D](f: T => D): Source[List[D]] = x.dsl.mapElem(x, f)

  /** Map elements using a DSL `Source` function. */
  infix inline def mapsElem[D](f: Source[T] => Source[D]): Source[List[D]] = x.dsl.mapsElem(x, f)

  /** Group elements by a key projection. */
  infix inline def group[K](key: Source[T] => Source[K]): Group[K, T] = x.dsl.group(x, key)

  /** Filter elements using a predicate `Source`. */
  infix inline def filter(p: Source[T] => Source[Boolean]): Source[List[T]] = x.dsl.filter(x, p)

  /** Distinct elements using identity as key. */
  inline def distinct: Source[List[T]] = this.distinctBy(e => e)

  /** Distinct elements using provided key projection. */
  infix inline def distinctBy[K](key: Source[T] => Source[K]): Source[List[T]] = x.dsl.distinctBy(x, key)
}