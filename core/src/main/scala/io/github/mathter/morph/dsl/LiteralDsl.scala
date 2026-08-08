package io.github.mathter.morph.dsl

/**
 * Factory for literal `Source` nodes representing constant values.
 *
 * Implementations should prefer by-name parameter `x` to defer evaluation until
 * DSL evaluation time where appropriate.
 */
trait LiteralDsl {
  /** Create a literal source wrapping value `x`. */
  def literal[T](x: => T): Source[T]
}
