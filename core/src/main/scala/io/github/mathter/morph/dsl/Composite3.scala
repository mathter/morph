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
 * Composite builder combining five sources; final step before creating a
 * `Source[D]` via `fun`.
 */
trait Composite3[T, T0, T1, T2, T3] {
  def fun[D](f: (T, T0, T1, T2, T3) => D)(implicit ctag: ClassTag[D]): Source[D]
}
