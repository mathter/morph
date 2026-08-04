package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Else, If, Source, Then}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

class IfEval[T](val conditionEval: Eval[Boolean])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[T], If[T], Then[T], Else[T] {
  private var thenEval: Eval[T] = null;

  private var elseEval: Eval[T] = null;

  override def evalI(using context: Context): Opt[T] =
    this.conditionEval.eval.flatMap(condition =>
      if (condition) {
        if (this.thenEval != null) {
          this.thenEval.eval
        } else {
          Opt.empty
        }
      } else {
        if (this.elseEval != null) {
          this.elseEval.eval
        } else {
          Opt.empty
        }
      }
    )

  override def Then(source: Source[T]): Then[T] = {
    Tracer.trace3()
    this.thenEval = source.asInstanceOf[Eval[T]]
    this
  }

  override def Else(source: Source[T]): Else[T] = {
    Tracer.trace3()
    this.elseEval = source.asInstanceOf[Eval[T]]
    this
  }

  override def If[T](condition: Source[Boolean]): If[T] = {
    Tracer.trace3()
    new IfEval[T](condition.asInstanceOf[Eval[Boolean]])
  }
}
