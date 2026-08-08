package io.github.mathter.morph.dsl

/**
 * Represents the `Else` branch of a conditional expression. An `Else[T]` is
 * itself a `Source[T]` (producing a value) and supports re-entering conditional
 * chaining via `If`/`if` which allows nested conditionals.
 */
trait Else[T] extends Source[T], When[T] {
  /** Start a new conditional `When` using the symbolic `If`. */
  infix def If[T](condition: Source[Boolean]): When[T]

  /** Start a new conditional `When` using the keyword-style `if`. */
  infix def `if`(condition: Source[Boolean]): When[T]
}
