package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

/**
 * Composite builder combining three sources. Use `fun` to produce a derived
 * value from three inputs and `composite` to extend the builder further.
 */
trait Composite1[T, T0, T1] {
  def fun[D](f: (T, T0, T1) => D)(implicit ctag: ClassTag[D]): Source[D]

  def composite[T2](source: Source[T2]): Composite2[T, T0, T1, T2]
}
