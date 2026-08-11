package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.eval.Context

private trait Accept[T] {
  def apply(opt: Opt[T], context: Context): Opt[T]
}
