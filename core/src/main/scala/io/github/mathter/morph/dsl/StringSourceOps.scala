package io.github.mathter.morph.dsl

import io.github.mathter.morph.dsl.Source

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
