package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.{Opt, PathMap}
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Tracer}

private class OriginSourceEval(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[PathMap] with Source[PathMap] {
  override def evalI(context: Context): Opt[PathMap] = Opt(context.origin.asImmutable)
}
