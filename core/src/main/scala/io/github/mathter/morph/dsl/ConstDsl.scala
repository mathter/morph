package io.github.mathter.morph.dsl

trait ConstDsl {
  def fls: Source[Boolean]

  def tr: Source[Boolean]

  def nil[T]: Source[T]
}
