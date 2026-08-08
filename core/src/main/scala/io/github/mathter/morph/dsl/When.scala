package io.github.mathter.morph.dsl

trait When[T] {
  infix def Then(source: Source[T]): Then[T]

  infix def `then`(source: Source[T]): Then[T]
}
