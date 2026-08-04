package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

class CustomEval[T, D](val eval: Eval[T], f: Opt[T] => Opt[D])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[D] {
  override def evalI(using context: Context): Opt[D] = {
    val option = this.eval.eval

    this.f.apply(option)
  }
}
