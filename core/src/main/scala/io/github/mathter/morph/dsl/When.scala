package io.github.mathter.morph.dsl

/**
 * Conditional builder starting from a boolean condition. `When` allows
 * attaching a `Then` branch to specify the value when the condition holds.
 */
trait When[T] {
  /** Attach a `Then` branch using the symbolic `Then`. */
  infix def Then(source: Source[T]): Then[T]

  /** Attach a `Then` branch using keyword-style `then`. */
  infix def `then`(source: Source[T]): Then[T]
}
