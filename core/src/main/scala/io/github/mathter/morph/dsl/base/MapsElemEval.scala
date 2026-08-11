package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class MapsElemEval[E, D](val listEval: Eval[List[E]], val f: Source[E] => Source[D])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[List[D]] {
  override def evalI(implicit context: Context): Opt[List[D]] = {
    this.listEval.eval.map(_.map(elem => new AbstractEval[E] {
        override def evalI(context: Context): Opt[E] = Opt(elem)
      })
      .map(this.f)
      .flatMap(_.asInstanceOf[Eval[D]].eval)
    )
  }
}
