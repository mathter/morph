package io.github.mathter.morph.dsl

/**
 * Provides constant literal sources for commonly used values.
 *
 * Implementations typically return statically-constructed `Source` nodes for
 * true/false or `nil` values so they can be reused across the DSL without
 * allocating new nodes repeatedly.
 */
trait ConstDsl {
  /** Boolean constant `false`. */
  def fls: Source[Boolean]

  /** Boolean constant `true`. */
  def tr: Source[Boolean]

  /** Null/nil constant for the given type. */
  def nil[T]: Source[T]
}
