package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Composite2, Composite3, Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import scala.reflect.ClassTag

private class Composite2Eval[T, T0, T1, T2](val t: Eval[T], val t0: Eval[T0], val t1: Eval[T1], val t2: Eval[T2])(implicit dsl: Dsl, tracer: Tracer) extends Composite2[T, T0, T1, T2] {
  override def fun[D](f: (T, T0, T1, T2) => D)(implicit ctag: ClassTag[D]): Source[D] = new AbstractEval[D] {
    override def evalI(implicit context: Context): Opt[D] =
      t.eval.flatMap(t => t0.eval.flatMap(t0 => t1.eval.flatMap(t1 => t2.eval.map(t2 => f.apply(t, t0, t1, t2)))))
  }

  override def composite[T3](source: Source[T3]): Composite3[T, T0, T1, T2, T3] = {
    implicit val tracer: Tracer = Tracer.trace3()
    new Composite3Eval(this.t, this.t0, this.t1, this.t2, source.asInstanceOf[Eval[T3]])
  }
}
