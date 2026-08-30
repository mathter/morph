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

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.dsl.Source
import io.github.mathter.morph.path.Path

/**
 * Extension helpers for `Source[PathMap]` enabling convenient `by(path)`
 * traversal to fetch nested values from a `PathMap` source.
 */
implicit class PathMapSourceOps(val x: Source[PathMap]) {
  /** Retrieve a value from the path map source by the provided `Path`. */
  def by[T](path: Path): Source[T] = x.dsl.by(x, path)
}