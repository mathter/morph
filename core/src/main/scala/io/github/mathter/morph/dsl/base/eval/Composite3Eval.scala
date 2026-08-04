package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Composite3, Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import scala.reflect.ClassTag

private class Composite3Eval[T, T0, T1, T2, T3](val t: Eval[T], val t0: Eval[T0], val t1: Eval[T1], val t2: Eval[T2], val t3: Eval[T3])(implicit dsl: Dsl, tracer: Tracer) extends Composite3[T, T0, T1, T2, T3] {
  override def fun[D](f: (T, T0, T1, T2, T3) => D)(implicit ctag: ClassTag[D]): Source[D] = new AbstractEval[D] {
    override def evalI(implicit context: Context): Opt[D] = {
      implicit val tracer: Tracer = Tracer.trace3()
      t.eval.flatMap(t => t0.eval.flatMap(t0 => t1.eval.flatMap(t1 => t2.eval.flatMap(t2 => t3.eval.map(t3 => f.apply(t, t0, t1, t2, t3))))))
    }
  }
}
