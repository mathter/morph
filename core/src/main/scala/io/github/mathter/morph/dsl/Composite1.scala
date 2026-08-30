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
 * Composite builder combining three sources. Use `fun` to produce a derived
 * value from three inputs and `composite` to extend the builder further.
 */
trait Composite1[T, T0, T1] {
  def fun[D](f: (T, T0, T1) => D)(implicit ctag: ClassTag[D]): Source[D]

  def composite[T2](source: Source[T2]): Composite2[T, T0, T1, T2]
}
