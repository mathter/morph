package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Composite1, Composite2, Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import scala.reflect.ClassTag

private class Composite1Eval[T, T0, T1](val t: Eval[T], val t0: Eval[T0], val t1: Eval[T1])(implicit dsl: Dsl, tracer: Tracer) extends Composite1[T, T0, T1] {
  override def fun[D](f: (T, T0, T1) => D)(implicit ctag: ClassTag[D]): Source[D] = new AbstractEval[D] {
    override def evalI(implicit context: Context): Opt[D] =
      t.eval.flatMap(t => t0.eval.flatMap(t0 => t1.eval.map(t1 => f.apply(t, t0, t1))))
  }

  override def composite[T2](source: Source[T2]): Composite2[T, T0, T1, T2] = {
    implicit val tracer: Tracer = Tracer.trace3()
    new Composite2Eval(t, t0, t1, source.asInstanceOf[Eval[T2]])
  }
}
