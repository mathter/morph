package io.github.mathter.morph.eval

import io.github.mathter.morph.data.Opt

trait Eval[T] {
  def eval(implicit context: Context): Opt[T]
}
