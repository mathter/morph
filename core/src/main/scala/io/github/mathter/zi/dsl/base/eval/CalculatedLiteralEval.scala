package io.github.mathter.zi.dsl.base.eval

import io.github.mathter.zi.data.Opt
import io.github.mathter.zi.dsl.Dsl
import io.github.mathter.zi.eval.{Context, Tracer}

class CalculatedLiteralEval[T](f: => T)(using dsl: Dsl, tracer: Tracer) extends AbstractEval[T] {

  override def evalI(using context: Context): Opt[T] = {
    val value: T = f
    Opt(value)
  }
}
