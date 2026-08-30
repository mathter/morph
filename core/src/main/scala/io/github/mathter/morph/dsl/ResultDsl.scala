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
 * Helpers to create result acceptors used for final validation or extraction of
 * computed values. Optionally accepts a `tag` source to annotate or identify
 * the result in downstream processing.
 */
trait ResultDsl {
  /** Create a generic result acceptor. */
  def result[T]: Acceptor[T]

  /** Create a result acceptor annotated with `tag`. */
  def result[T](tag: Source[Any]): Acceptor[T]
}
