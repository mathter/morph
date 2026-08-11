package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.eval.{Context, Tracer}

private class LiteralEval[T](val literal: T)(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[T] {
  override def evalI(context: Context): Opt[T] = {
    Opt(this.literal)
  }

  override def pure: Boolean = true
}
