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

private class MapsElemEval[E, D](val listEval: Eval[List[E]], val f: Source[E] => Source[D])(implicit dsl: Dsl, tracer: Tracer) extends AbstractEval[List[D]] {
  override def evalI(implicit context: Context): Opt[List[D]] = {
    this.listEval.eval.map(_.map(elem => new AbstractEval[E] {
        override def evalI(context: Context): Opt[E] = Opt(elem)
      })
      .map(this.f)
      .flatMap(_.asInstanceOf[Eval[D]].eval)
    )
  }
}
