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

import io.github.mathter.morph.data.Opt

/**
 * Represents a node in the DSL that can produce a value of type `T` when
 * evaluated against a source (for example a `PathMap`).
 *
 * `Source` is the central abstraction of the DSL. It provides combinators for
 * mapping, composing and transforming values. Implementations are expected to
 * be immutable and side-effect free; side-effects are represented by
 * `Acceptor`/`Terminal` nodes instead.
 */
trait Source[T] {
  /** Reference to the `Dsl` implementation that created this source. */
  def dsl: Dsl

  /** Map this source to another `Source` using a function that receives the source itself. */
  def maps[D, DS <: Source[D]](f: Source[T] => Source[D]): DS

  /** Map the produced value using a pure function into a new `Source`. */
  def map[D, DS <: Source[D]](f: T => D): DS

  /** Transform this source using an `Opt`-aware function (preserves missing semantics). */
  infix def customOpt[D](f: Opt[T] => Opt[D]): Source[D]

  /** Transform the produced value using a plain function. */
  infix def custom[D](f: T => D): Source[D]

  /** Compose this source with another to form a `Composite` builder. */
  infix def composite[T0](source: Source[T0]): Composite[T, T0]

  /** Cast this source to a different expected result type. */
  def as[D]: Source[D]

  /** Compare equality with another source; produces a boolean `Source`. */
  infix def equalsTo(another: Source[T]): Source[Boolean]

  /** Alias for `equalsTo`. */
  def ==(another: Source[T]): Source[Boolean] = this.equalsTo(another)

  /** Whether this source is pure (no external dependencies). */
  def pure: Boolean

  /** Set purity flag and return the modified source. */
  def pure(pure: Boolean): Source[T]

  /** Provide a default when the source evaluates to `null`. */
  def ifNull(default: => T): Source[T]

  /** Provide a default when the source evaluates to an empty value. */
  def ifEmpty(default: => T): Source[T]

  /** Provide a default when the source evaluates to `null` or empty. */
  def ifNullOrEmpty(default: => T): Source[T]

  /** Mark the source to produce an error if `null` is encountered during evaluation. */
  def errorIfNull: Source[T]

  /** Mark the source to produce an error if an empty value is encountered during evaluation. */
  def errorIfEmpty: Source[T]

  /** Mark the source to produce an error if `null` or empty is encountered during evaluation. */
  def errorIfNullOrEmpty: Source[T]
}