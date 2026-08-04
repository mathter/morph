package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

class MapEval[T, D](val source: Source[T], f: T => D)(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[D] {
  override def evalI(using context: Context): Opt[D] = {
    this.source.asInstanceOf[Eval[T]].eval.map(f)
  }
}
