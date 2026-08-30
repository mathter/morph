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
 * Provides constant literal sources for commonly used values.
 *
 * Implementations typically return statically-constructed `Source` nodes for
 * true/false or `nil` values so they can be reused across the DSL without
 * allocating new nodes repeatedly.
 */
trait ConstDsl {
  /** Boolean constant `false`. */
  def fls: Source[Boolean]

  /** Boolean constant `true`. */
  def tr: Source[Boolean]

  /** Null/nil constant for the given type. */
  def nil[T]: Source[T]
}
