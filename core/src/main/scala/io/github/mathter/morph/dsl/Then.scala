package io.github.mathter.morph.dsl

/**
 * Represents the `Then` branch in a conditional expression. A `Then[T]` is a
 * `Source[T]` and supports attaching an `Else` branch to form a complete
 * conditional expression.
 */
trait Then[T] extends Source[T] {
  /** Attach an `Else` branch using the symbolic `Else`. */
  infix def Else(source: Source[T]): Else[T]

  /** Attach an `Else` branch using keyword-style `else`. */
  infix def `else`(source: Source[T]): Else[T]
}
