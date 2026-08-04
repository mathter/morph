package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

trait Composite[T, T0] {
  def fun[D](f: (T, T0) => D)(implicit ctag: ClassTag[D]): Source[D]

  def composite[T1](source: Source[T1]): Composite1[T, T0, T1]
}
