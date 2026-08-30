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
 * Top-level DSL interface aggregating available DSL building blocks.
 *
 * Implementations of `Dsl` provide concrete factories and helpers to create
 * `Source` instances, compose expressions and evaluate them against sources
 * such as `PathMap`.
 *
 * The `Dsl` trait composes smaller traits that focus on specific domains
 * (literals, lists, path-map access, etc.) and exposes convenience primitives
 * like `nothing` and `when` for conditional construction.
 */
trait Dsl
  extends LiteralDsl
    with ListDsl
    with OriginDsl
    with ResultDsl
    with PathMapDsl
    with ConstDsl {

  /** A `Source` that represents the absence of a value. */
  def nothing[T]: Source[T]

  /** Identity wrapper for a `Source`. */
  def unit[T](source: Source[T]): Source[T] = source

  /** Create a conditional `When` builder using the boolean `condition`. */
  def when[T](condition: Source[Boolean]): When[T]
}

/** Implicit conversion from plain values to literal `Source` via an in-scope `Dsl`. */
given [T](using dsl: Dsl): Conversion[T, Source[T]] with {
  override def apply(x: T): Source[T] = dsl.literal(x)
}