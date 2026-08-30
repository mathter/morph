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

import scala.reflect.ClassTag

/**
 * Represents a composite expression combining two sources.
 *
 * A `Composite` allows defining a function that produces a derived value from
 * two inputs and then continues composing with additional sources via
 * `composite(...)` to build larger tuples.
 *
 * Implementations should create a `Source[D]` when `fun` is called, wrapping
 * the provided function `f` into the DSL evaluation model.
 */
trait Composite[T, T0] {
  /** Create a composed source by applying `f` to the evaluated inputs. */
  def fun[D](f: (T, T0) => D)(implicit ctag: ClassTag[D]): Source[D]

  /** Extend the composite with one more source to form a `Composite1`. */
  def composite[T1](source: Source[T1]): Composite1[T, T0, T1]
}
