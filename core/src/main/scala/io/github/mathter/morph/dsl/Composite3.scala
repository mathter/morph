package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

/**
 * Composite builder combining five sources; final step before creating a
 * `Source[D]` via `fun`.
 */
trait Composite3[T, T0, T1, T2, T3] {
  def fun[D](f: (T, T0, T1, T2, T3) => D)(implicit ctag: ClassTag[D]): Source[D]
}
