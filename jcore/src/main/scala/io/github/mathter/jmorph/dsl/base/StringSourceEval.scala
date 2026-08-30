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

import io.github.mathter.jmorph.dsl.{BooleanSource, NumberSource, StringSource}
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Dsl
import io.github.mathter.morph.dsl.base.AbstractEval
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import java.lang

class StringSourceEval(private val eval: Eval[String])(implicit dsl: Dsl, tracer: Tracer = Tracer.trace5())
  extends AbstractEval[String] with StringSource {
  override def evalI(using context: Context): Opt[String] = this.eval.eval

  override def toUpperCase: StringSource =
    new StringSourceEval(this.eval) {
      override def evalI(using context: Context): Opt[String] = StringSourceEval.this.eval.eval.map(_.toUpperCase)
    }

  override def toLowerCase: StringSource =
    new StringSourceEval(this.eval) {
      override def evalI(using context: Context): Opt[String] = StringSourceEval.this.eval.eval.map(_.toLowerCase)
    }

  override def replaceAll(regexpr: String, replacement: String): StringSource =
    new StringSourceEval(this.eval) {
      override def evalI(using context: Context): Opt[String] = StringSourceEval.this.eval.eval.map(_.replaceAll(regexpr, replacement))
    }

  override def length(): NumberSource[Integer] = {
    import JavaNumeric.IntegerNumeric

    given numeric: Numeric[Integer] = Numeric[Integer]

    new NumberSourceEval[Integer](null) {
      override def evalI(using context: Context): Opt[Integer] = StringSourceEval.this.eval.eval.map(_.length)
    }
  }

  override def trim(): StringSource =
    new StringSourceEval(this.eval) {
      override def evalI(using context: Context): Opt[String] = StringSourceEval.this.eval.eval.map(_.trim)
    }

  override def isEmpty: BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[lang.Boolean] =
        StringSourceEval.this.eval.eval.map(e => e == null || e.isEmpty)
    }

  override def notEmpty: BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[lang.Boolean] =
        StringSourceEval.this.eval.eval.map(e => e != null && e.nonEmpty)
    }

  override def isBlank: BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[lang.Boolean] =
        StringSourceEval.this.eval.eval.map(e => e == null || e.isBlank)
    }

  override def notBlank: BooleanSource =
    new BooleanSourceEval(null) {
      override def evalI(using context: Context): Opt[lang.Boolean] =
        StringSourceEval.this.eval.eval.map(e => e != null && !e.isBlank)
    }
}
