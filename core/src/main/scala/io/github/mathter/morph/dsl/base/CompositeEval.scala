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
package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.{Composite, Composite1, Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import scala.reflect.ClassTag

private class CompositeEval[T, T0](val t: Eval[T], val t0: Eval[T0])(implicit dsl: Dsl, tracer: Tracer) extends Composite[T, T0] {
  override def fun[D](f: (T, T0) => D)(implicit ctag: ClassTag[D]): Source[D] = new AbstractEval[D] {
    override def evalI(implicit context: Context): Opt[D] = t.eval.flatMap(t => t0.eval.map(t0 => f.apply(t, t0)))
  }

  override def composite[T1](source: Source[T1]): Composite1[T, T0, T1] = {
    implicit val tracer: Tracer = Tracer.trace3()
    new Composite1Eval(this.t, this.t0, source.asInstanceOf[Eval[T1]])
  }
}
