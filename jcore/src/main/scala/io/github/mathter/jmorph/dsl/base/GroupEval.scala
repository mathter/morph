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