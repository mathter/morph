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
import io.github.mathter.morph.eval.Terminal
import io.github.mathter.morph.path.Path

/**
 * Convenience extension methods for `Acceptor[PathMap]` to improve ergonomics
 * when binding acceptors to specific `Path` locations.
 *
 * Example usage:
 *   obj.by(path).from(source)
 *
 * These helpers bridge the DSL and path-based data model.
 */
implicit class AcceptorOps(x: Acceptor[PathMap]) {
  /** Bind the acceptor to a path within the `PathMap`. */
  infix inline def by[T](path: Path): Acceptor[T] = x.dsl.by(x, path)

  /**
   * Shortcut to update `path` with `source` using this acceptor and obtain a
   * terminal expression.
   */
  inline def update[T](path: Path, source: Source[T]): Source[T] & Terminal =
    this.by(path).from(source)
}