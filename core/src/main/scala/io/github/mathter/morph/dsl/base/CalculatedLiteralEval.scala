package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.eval.{Context, Tracer}

private class CalculatedLiteralEval[T](f: => T)(using dsl: Dsl, tracer: Tracer) extends AbstractEval[T] {

  override def evalI(using context: Context): Opt[T] = {
    val value: T = f
    Opt(value)
  }
}
