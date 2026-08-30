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
 * Represents the `Then` branch in a conditional expression. A `Then[T]` is a
 * `Source[T]` and supports attaching an `Else` branch to form a complete
 * conditional expression.
 */
trait Then[T] extends Source[T] {
  /** Attach an `Else` branch using the symbolic `Else`. */
  infix def Else(source: Source[T]): Else[T]

  /** Attach an `Else` branch using keyword-style `else`. */
  infix def `else`(source: Source[T]): Else[T]
}
