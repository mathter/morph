package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.eval.{Context, Tracer}

private class NothingEval[T](implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[T] {
  override def evalI(context: Context): Opt[T] = Opt.empty[T]
}
