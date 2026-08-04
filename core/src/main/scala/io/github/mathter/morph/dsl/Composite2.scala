package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

trait Composite2[T, T0, T1, T2] {
  def fun[D](f: (T, T0, T1, T2) => D)(implicit ctag: ClassTag[D]): Source[D]

  def composite[T3](source: Source[T3]): Composite3[T, T0, T1, T2, T3]
}
