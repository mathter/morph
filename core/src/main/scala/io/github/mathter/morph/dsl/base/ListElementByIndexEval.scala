package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class ListElementByIndexEval[T](val listEval: Eval[List[T]], indexEval: Eval[Int])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[T] {
  override def evalI(using context: Context): Opt[T] = {
    this.listEval.eval
      .flatMap(list => {
        this.indexEval.eval
          .filter(index => index >= 0 && index < list.length)
          .map(index => list(index))
      })
  }
}
