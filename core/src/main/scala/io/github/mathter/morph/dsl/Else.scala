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
 * Represents the `Else` branch of a conditional expression. An `Else[T]` is
 * itself a `Source[T]` (producing a value) and supports re-entering conditional
 * chaining via `If`/`if` which allows nested conditionals.
 */
trait Else[T] extends Source[T], When[T] {
  /** Start a new conditional `When` using the symbolic `If`. */
  infix def If[T](condition: Source[Boolean]): When[T]

  /** Start a new conditional `When` using the keyword-style `if`. */
  infix def `if`(condition: Source[Boolean]): When[T]
}
