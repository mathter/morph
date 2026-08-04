package io.github.mathter.morph.dsl

trait If[T] {
  def Then(source: Source[T]): Then[T]
}
