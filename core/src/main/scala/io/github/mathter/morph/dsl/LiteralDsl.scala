package io.github.mathter.morph.dsl

trait LiteralDsl {
  def literal[T](x: => T): Source[T]
}
