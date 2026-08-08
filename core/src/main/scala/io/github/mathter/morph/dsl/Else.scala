package io.github.mathter.morph.dsl

trait Else[T] extends Source[T], When[T] {
  infix def If[T](condition: Source[Boolean]): When[T]

  infix def `if`(condition: Source[Boolean]): When[T]
}
