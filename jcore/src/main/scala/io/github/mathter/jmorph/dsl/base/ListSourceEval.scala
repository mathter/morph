// Copyright (c) 2026 mathter
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, without
// warranties or condition of any kind. You may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// All rights reserved.
package io.github.mathter.jmorph.dsl.base

import io.github.mathter.jmorph.dsl.base.BaseDsl.{*, given}
import io.github.mathter.jmorph.dsl.{Group, ListSource}
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.base.{AbstractEval, given}
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
