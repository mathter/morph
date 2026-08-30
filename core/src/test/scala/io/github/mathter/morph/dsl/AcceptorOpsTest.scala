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
package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.dsl.base.{BaseContext, BaseDsl, Evaluator}
import io.github.mathter.morph.eval.Terminal
import org.junit.jupiter.api.{Assertions, Test}

class AcceptorOpsTest {
  @Test
  def test(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val a = dsl.result;
    Assertions.assertNotNull(a)
    val a0 = dsl.result[PathMap].by[String]("p0")
    Assertions.assertNotNull(a0)

    var r = Evaluator.evalSource(a0)
    Assertions.assertNotNull(r)
    Assertions.assertTrue(r.isEmpty)

    Evaluator.eval(dsl.result.from(PathMap.empty).asInstanceOf[Terminal])
    val s0 = a0.from(dsl.literal("Hello"))
    Assertions.assertNotNull(s0)
    r = Evaluator.eval(s0)
    Assertions.assertNotNull(r)
    Assertions.assertEquals("Hello", r.get)

    r = Evaluator.evalSource(a0)
    Assertions.assertNotNull(r)
    Assertions.assertEquals("Hello", r.get)
  }

  @Test
  def testUpdate(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    Evaluator.eval(dsl.result.from(dsl.literal(PathMap.empty)))
    val s: Source[String] = dsl.result("p0") = "Hello"
    Assertions.assertNotNull(s)

    val r = Evaluator.evalSource(s)
    Assertions.assertNotNull(r)
    Assertions.assertEquals("Hello", r.get)
  }
}
