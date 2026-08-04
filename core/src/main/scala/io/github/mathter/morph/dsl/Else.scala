package io.github.mathter.morph.dsl

trait Else[T] extends Source[T], If[T] {
  def If[T](condition: Source[Boolean]): If[T]
}
