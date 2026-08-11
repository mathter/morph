package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Acceptor, Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Terminal, Tracer}

abstract class AbstractAcceptorEval[T](val acceptor: Accept[T])(implicit dsl: Dsl, tracer: Tracer)
  extends AbstractEval[T] with Acceptor[T] {

  override def from(source: Source[T]): Source[T] & Terminal = {
    new AbstractEval[T] with Terminal() {
      override def evalI(implicit context: Context): Opt[T] = {
        acceptor.apply(source.asInstanceOf[Eval[T]].eval, context)
      }
    }
  }
}
