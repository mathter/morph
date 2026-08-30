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

import io.github.mathter.morph.eval.Terminal

/**
 * A specialized [[Source]] that represents an acceptance point for values.
 *
 * An `Acceptor` is used when a DSL expression both produces a value and
 * accepts (consumes) an input source. Typical use-cases include writing a
 * value into a `PathMap` or validating and binding a value from a source.
 *
 * The `from` method couples an acceptor with a producing `Source`, returning
 * a `Terminal` to indicate the expression is a leaf with side-effects or a
 * final result in evaluation.
 */
trait Acceptor[T] extends Source[T] {
  /**
   * Bind this acceptor to a producing source.
   *
   * @param source the value-producing source
   * @return a composed `Source[T]` that is also a `Terminal` (end of an
   *         evaluation chain, often with side-effects)
   */
  def from(source: Source[T]): Source[T] & Terminal
}
