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

import io.github.mathter.jmorph.dsl.NumberSource
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.dsl.base.AbstractEval
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import scala.math.Integral.Implicits.infixIntegralOps


class NumberSourceEval[T <: Number]
(protected val eval: Eval[T])(using dsl: Dsl, tracer: Tracer = Tracer.trace5(), numeric: Integral[T])
  extends AbstractEval[T]
    with NumberSource[T] {
  override def evalI(using context: Context): Opt[T] = eval.eval

  override def plus(source: NumberSource[T]): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.flatMap(left => source.asInstanceOf[Eval[T]].eval.map(right => left + right))
    }

  override def minus(source: NumberSource[T]): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.flatMap(left => source.asInstanceOf[Eval[T]].eval.map(right => left - right))
    }

  override def multiply(source: NumberSource[T]): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.flatMap(left => source.asInstanceOf[Eval[T]].eval.map(right => left * right))
    }

  override def divide(source: NumberSource[T]): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.flatMap(left => source.asInstanceOf[Eval[T]].eval.map(right => left / right))
    }

  override def rem(source: NumberSource[T]): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.flatMap(left => source.asInstanceOf[Eval[T]].eval.map(right => left % right))
    }

  override def abs(): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.map(_.abs)
    }

  override def negate(): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.map(-_)
    }

  override def sign(): NumberSource[T] =
    new NumberSourceEval[T](this.eval) {
      override def evalI(using context: Context): Opt[T] =
        this.eval.eval.map(_.sign)
    }
}
