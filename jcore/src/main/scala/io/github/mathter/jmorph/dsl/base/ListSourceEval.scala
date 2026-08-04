package io.github.mathter.jmorph.dsl.base

import BaseDsl.{*, given}
import io.github.mathter.jmorph.dsl.{Group, ListSource}
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.base.eval.AbstractEval
import io.github.mathter.morph.dsl.base.given
import io.github.mathter.morph.dsl.{Dsl, Source, Group as zGroup, given}
import io.github.mathter.morph.eval.{Context, Tracer}

import java.util
import java.util.function

class ListSourceEval[T]
(protected val source: Source[util.List[T]])
(implicit dsl: Dsl, tracer: Tracer = Tracer.trace5())
  extends AbstractEval[util.List[T]]() with ListSource[T] {

  override def evalI(using context: Context): Opt[util.List[T]] = this.source.eval

  inline override def first(): Source[T] = javaListSource2ListSource.apply(this).first

  inline override def last(): Source[T] = javaListSource2ListSource.apply(this).last

  inline override def index(index: Source[Integer]): Source[T] = javaListSource2ListSource.apply(this).index(index)

  override def mapElem[D](mapper: function.Function[_ >: T, _ <: D]): ListSource[D] = {
    val f: T => D = t => mapper.apply(t)
    val scalaListSource = javaListSource2ListSource.apply(this)
    val scalaMappedElementListSource = scalaListSource.mapElem(f)

    listSource2JavaListSource.apply(scalaMappedElementListSource)
  }

  override def mapsElem[D](mapper: function.Function[Source[T], Source[D]]): ListSource[D] = {
    val f: Source[T] => Source[D] = s => mapper.apply(s)
    val scalaListSource = javaListSource2ListSource.apply(this)
    val scalaMappedElementListSource = scalaListSource.mapsElem(f)

    listSource2JavaListSource.apply(scalaMappedElementListSource)
  }

  override def group[K](keyMapper: function.Function[Source[T], Source[K]]): Group[K, T] = {
    val f: Source[T] => Source[K] = s => keyMapper.apply(s)
    val scalaListSource = javaListSource2ListSource.apply(this).asInstanceOf[Source[List[T]]]
    val scalaGroup: zGroup[K, T] = scalaListSource.group(f)

    new GroupEval[K, T](scalaGroup)
  }
}
