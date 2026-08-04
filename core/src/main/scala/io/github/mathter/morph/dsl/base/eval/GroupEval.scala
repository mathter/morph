package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Dsl, Group, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

class GroupEval[K, E](val listEval: Eval[List[E]], val key: Source[E] => Source[K])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[List[(K, List[E])]], Group[K, E] {
  override def evalI(using context: Context): Opt[List[(K, List[E])]] = {
    this.listEval.eval.map(_.groupBy(e => {
        val literal = new LiteralEval(e)
        key.apply(literal).asInstanceOf[Eval[K]].eval.get
      }))
      .map(_.toList)
  }

  override def apply[D](f: (Source[K], Source[List[E]]) => Source[D]): Source[List[D]] = {
    new AbstractEval[List[D]]() {
      override def evalI(using context: Context): Opt[List[D]] = {
        GroupEval.this.eval.map(_.flatMap((l, r) =>
          f.apply(new LiteralEval(l), new LiteralEval(r)).asInstanceOf[Eval[D]].eval
        ))
      }
    }
  }
}
