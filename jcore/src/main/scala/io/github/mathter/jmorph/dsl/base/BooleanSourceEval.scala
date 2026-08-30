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

import io.github.mathter.jmorph.dsl.BooleanSource
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.base.AbstractEval
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
