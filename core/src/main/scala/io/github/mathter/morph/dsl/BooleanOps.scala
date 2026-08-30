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
 * Boolean-specific helpers for [[Source]] values producing `Boolean`.
 *
 * Provides logical combinators (and, or, xor, not) implemented as DSL
 * compositions. Also supplies convenient symbolic aliases (`&&`, `||`, `!`)
 * and a `Then` helper to start a conditional expression when used with
 * `Dsl.when`.
 */
implicit class BooleanOps(x: Source[Boolean]) {
  infix inline def and(y: Source[Boolean]): Source[Boolean] = x.composite(y).fun((x, y) => x && y)

  infix inline def or(y: Source[Boolean]): Source[Boolean] = x.composite(y).fun((x, y) => x || y)

  infix inline def xor(y: Source[Boolean]): Source[Boolean] = x.composite(y).fun((x, y) => x ^ y)

  infix inline def not: Source[Boolean] = x.custom(!_)

  infix inline def &&(y: Source[Boolean]): Source[Boolean] = this.and(y)

  infix inline def ||(y: Source[Boolean]): Source[Boolean] = this.or(y)

  infix inline def unary_! : Source[Boolean] = this.not

  infix inline def ^(y: Source[Boolean]): Source[Boolean] = this.xor(y)

  /** Start a conditional `Then` branch using this boolean as the condition. */
  infix inline def Then[T](source: Source[T]): Then[T] = x.dsl.when(x).Then(source)
}