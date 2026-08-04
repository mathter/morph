package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

trait Composite1[T, T0, T1] {
  def fun[D](f: (T, T0, T1) => D)(implicit ctag: ClassTag[D]): Source[D]

  def composite[T2](source: Source[T2]): Composite2[T, T0, T1, T2]
}
