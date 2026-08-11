package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class MapsEval[T, D](val source: Source[T], f: Source[T] => Source[D])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[D] {
  override def evalI(using context: Context): Opt[D] = {
    val resultingEval = this.f.apply(this.source).asInstanceOf[Eval[D]]

    resultingEval.eval
  }
}
