package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class FilterEval[T](val listEval: Eval[List[T]], p: Source[T] => Source[Boolean])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[List[T]] {
  override def evalI(using context: Context): Opt[List[T]] = this.listEval.eval.map(list => list.filter(e =>
    this.p.apply(LiteralEval[T](e)).asInstanceOf[Eval[Boolean]].eval.getOrElse(false)
  ))
}
