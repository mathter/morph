package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.{Opt, PathMap}
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.eval.{Context, Eval, Tracer}
import io.github.mathter.morph.path.Path

private class PathMapByPathAcceptor[T](val eval: Eval[PathMap], val path: Path)(implicit dsl: Dsl, tracer: Tracer) extends AbstractAcceptorEval[T]((opt, context) => {
  eval.eval(context).flatMap(pathMap => opt.map(e => {
    pathMap.put(path, e)
    e
  }))
}) {
  override def evalI(implicit context: Context): Opt[T] = {
    this.eval.eval.flatMap(e => e.get(this.path))
  }
}
