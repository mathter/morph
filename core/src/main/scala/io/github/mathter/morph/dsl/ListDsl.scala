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
package io.github.mathter.morph.dsl

/**
 * List-handling primitives for the DSL. These operations operate on `Source`
 * values that produce `List[T]` and return new `Source` instances for list
 * operations.
 *
 * Provided operations include element selection (`first`, `last`, `index`),
 * grouping, filtering, and mapping helpers to transform list elements.
 */
trait ListDsl {
  /** First element of the provided list source. */
  def first[T](source: Source[List[T]]): Source[T]

  /** Last element of the provided list source. */
  def last[T](source: Source[List[T]]): Source[T]

  /** Element at the index given by the `index` source. */
  def index[T](source: Source[List[T]], index: Source[Int]): Source[T]

  /** Group list elements by a key-producing function. */
  def group[K, E](source: Source[List[E]], key: Source[E] => Source[K]): Group[K, E]

  /** Filter list elements by a predicate expressed as a `Source`. */
  def filter[T](source: Source[List[T]], p: Source[T] => Source[Boolean]): Source[List[T]]

  /** Return a list of distinct elements using `key` to compute uniqueness. */
  def distinctBy[K, T](source: Source[List[T]], key: Source[T] => Source[K]): Source[List[T]]

  /** Map elements with a plain function. */
  def mapElem[T, D](source: Source[List[T]], f: T => D): Source[List[D]]

  /** Map elements with a DSL `Source` function. */
  def mapsElem[T, D](source: Source[List[T]], f: Source[T] => Source[D]): Source[List[D]]
}
