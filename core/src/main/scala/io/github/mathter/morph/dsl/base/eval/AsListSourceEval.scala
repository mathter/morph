package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

class AsListSourceEval[E](val eval: Eval[E])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[List[E]] {
  override def evalI(implicit context: Context): Opt[List[E]] = this.eval.eval.map(List(_))
}
