package io.github.mathter.zi.dsl

trait LiteralDsl {
  def literal[T](x: => T): Source[T]
}
