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
 * Represents a grouped collection resulting from `ListDsl.group`.
 *
 * A `Group[K,E]` produces a `List[(K, List[E])]` where each tuple contains the
 * group key and the list of elements belonging to that key. The `apply` method
 * allows mapping each group to a derived value using a function that receives
 * the group key and the group's element-list as `Source` instances.
 */
trait Group[K, E] extends Source[List[(K, List[E])]] {
  def apply[D](f: (Source[K], Source[List[E]]) => Source[D]): Source[List[D]]
}
