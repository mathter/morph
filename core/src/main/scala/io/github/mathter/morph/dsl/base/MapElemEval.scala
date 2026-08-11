package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class MapElemEval[E, D](val listEval: Eval[List[E]], val f: E => D)(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[List[D]] {
  override def evalI(implicit context: Context): Opt[List[D]] = this.listEval.eval.map(_.map(this.f))
}
