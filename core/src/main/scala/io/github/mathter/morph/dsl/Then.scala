package io.github.mathter.morph.dsl

trait Then[T] extends Source[T] {
  infix def Else(source: Source[T]): Else[T]

  infix def `else`(source: Source[T]): Else[T]
}
