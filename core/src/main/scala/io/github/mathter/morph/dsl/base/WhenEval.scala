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
import io.github.mathter.morph.dsl.{Dsl, Else, Source, Then, When}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class WhenEval[T](val conditionEval: Eval[Boolean])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[T], When[T], Then[T], Else[T] {
  private var thenEval: Eval[T] = null;

  private var elseEval: Eval[T] = null;

  override def evalI(using context: Context): Opt[T] =
    this.conditionEval.eval.flatMap(condition =>
      if (condition) {
        if (this.thenEval != null) {
          this.thenEval.eval
        } else {
          Opt.empty
        }
      } else {
        if (this.elseEval != null) {
          this.elseEval.eval
        } else {
          Opt.empty
        }
      }
    )

  override def Then(source: Source[T]): Then[T] = {
    Tracer.trace3()
    this.thenEval = source.asInstanceOf[Eval[T]]
    this
  }

  inline infix override def `then`(source: Source[T]): Then[T] = {
    Tracer.trace3()
    this.thenEval = source.asInstanceOf[Eval[T]]
    this
  }

  override def Else(source: Source[T]): Else[T] = {
    Tracer.trace3()
    this.elseEval = source.asInstanceOf[Eval[T]]
    this
  }

  inline infix override def `else`(source: Source[T]): Else[T] = {
    Tracer.trace3()
    this.elseEval = source.asInstanceOf[Eval[T]]
    this
  }

  override def If[T](condition: Source[Boolean]): When[T] = {
    Tracer.trace3()
    new WhenEval[T](condition.asInstanceOf[Eval[Boolean]])
  }

  override def `if`(condition: Source[Boolean]): When[T] = {
    Tracer.trace3()
    new WhenEval[T](condition.asInstanceOf[Eval[Boolean]])
  }
}
