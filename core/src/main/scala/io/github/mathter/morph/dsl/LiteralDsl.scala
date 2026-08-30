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
 * Factory for literal `Source` nodes representing constant values.
 *
 * Implementations should prefer by-name parameter `x` to defer evaluation until
 * DSL evaluation time where appropriate.
 */
trait LiteralDsl {
  /** Create a literal source wrapping value `x`. */
  def literal[T](x: => T): Source[T]
}
