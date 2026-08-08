package io.github.mathter.jmorph.dsl.base

import io.github.mathter.jmorph.dsl.BooleanSource
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.base.eval.AbstractEval
import io.github.mathter.morph.dsl.{Dsl, Source, Then}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import java.lang.Boolean

class BooleanSourceEval(private val eval: Eval[Boolean])(implicit dsl: Dsl, tracer: Tracer = Tracer.trace5())
  extends AbstractEval[Boolean] with BooleanSource {
  override def evalI(using context: Context): Opt[Boolean] = this.eval.eval

  override def and(other: Source[Boolean]): BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[Boolean] =
        BooleanSourceEval.this.eval.eval.flatMap(left =>
          other.asInstanceOf[Eval[Boolean]].eval.map(right => left && right))
    }

  override def or(other: Source[Boolean]): BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[Boolean] =
        BooleanSourceEval.this.eval.eval.flatMap(left =>
          other.asInstanceOf[Eval[Boolean]].eval.map(right => left || right))
    }

  override def xor(other: Source[Boolean]): BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[Boolean] =
        BooleanSourceEval.this.eval.eval.flatMap(left =>
          other.asInstanceOf[Eval[Boolean]].eval.map(right => left ^ right))
    }

  override def not(): BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[Boolean] =
        BooleanSourceEval.this.eval.eval.map(!_)
    }

  override def `then`[T](source: Source[T]): Then[T] = {
    given dsl: Dsl = this.dsl

    this.dsl.when(this.asInstanceOf[Source[scala.Boolean]]).Then(source)
  }
}
