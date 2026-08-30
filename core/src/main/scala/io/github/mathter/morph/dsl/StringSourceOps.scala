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

import io.github.mathter.morph.dsl.Source

/**
 * String-specific helpers for `Source[String]` instances. Provides common
 * string operations such as case conversion, trimming, inspection and simple
 * regex matching implemented as DSL transformations.
 */
implicit class StringSourceOps(private val x: Source[String]) {
  def toUpperCase: Source[String] = x.custom(s => s.toUpperCase)

  inline def toLowerCase: Source[String] = x.custom(s => s.toLowerCase)

  inline def replaceAll(regexpr: String, replacement: String): Source[String] = x.custom(s => s.replaceAll(regexpr, replacement))

  inline def length: Source[Int] = x.custom(e => if (e != null) e.length else 0)

  inline def isEmpty: Source[Boolean] = x.custom(e => e == null || e.isEmpty)

  inline def nonEmpty: Source[Boolean] = x.custom(e => e != null && e.nonEmpty)

  inline def isBlank: Source[Boolean] = x.custom(e => e == null || e.isBlank)

  inline def nonBlank: Source[Boolean] = x.custom(e => e != null && !e.isBlank)

  inline def matches(regexpr: String): Source[Boolean] = x.custom(e => e != null && e.matches(regexpr))

  inline def trim: Source[String] = x.custom(_.trim)
}