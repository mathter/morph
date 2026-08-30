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
import io.github.mathter.morph.dsl.{Dsl, Source}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

private class ResultEval[T](val tag: Eval[Any])(implicit dsl: Dsl, tracer: Tracer) extends AbstractAcceptorEval[T](
  (opt, context) => {
    tag.eval(using context).flatMap(e => context.target(e, opt))
  }
) {
  override def evalI(implicit context: Context): Opt[T] = {
    tag.eval.flatMap(e => context.target(e))
  }
}