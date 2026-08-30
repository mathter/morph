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
package io.github.mathter.morph.processing

import io.github.mathter.morph.data.PathMap

/**
 * Serializer abstraction for converting a PathMap into a target
 * representation.
 *
 * @tparam T resulting serialized type (for example String or XML DOM)
 */
trait Serializer[T] {
  /**
   * Serialize the provided PathMap into an instance of T.
   *
   * @param pathMap the PathMap containing parsed data
   * @return serialized representation of the input
   */
  def serialize(pathMap: PathMap): T
}
