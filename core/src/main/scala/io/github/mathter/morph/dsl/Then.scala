package io.github.mathter.morph.dsl

trait Then[T] extends Source[T] {
  def Else(source: Source[T]): Else[T]
}
