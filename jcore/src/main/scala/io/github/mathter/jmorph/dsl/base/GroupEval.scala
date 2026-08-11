package io.github.mathter.jmorph.dsl.base

import io.github.mathter.jmorph.dsl.Group
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl
import io.github.mathter.morph.dsl.base.{AbstractEval, given}
import io.github.mathter.morph.dsl.{Dsl, Source, Group as zGroup}
import io.github.mathter.morph.eval.{Context, Tracer}
import org.apache.commons.lang3.tuple.Pair

import java.util

class GroupEval[K, T](group: zGroup[K, T])
                     (implicit dsl: Dsl, tracer: Tracer = Tracer.trace5())
  extends ListSourceEval[Pair[K, util.List[T]]](null) with Group[K, T] {
  override def evalI(using context: Context): Opt[util.List[Pair[K, util.List[T]]]] = {
    import scala.jdk.CollectionConverters.given

    this.group.eval.map(e => e.map((key, list) => Pair.of(key, list.asJava)).asJava)
  }
}