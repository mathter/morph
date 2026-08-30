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
import org.junit.jupiter.api.{Assertions, Test}

class SourcePathMapTest {
  @Test
  def testBy(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()

    val pm = PathMap.empty
    pm("path0/path1") = "value1"
    pm("path0") = "valu0"

    val s = dsl.literal(pm)

    val result = Evaluator.evalSource(s)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals("value1", result.get("path0/path1").get)
    Assertions.assertEquals(2, result.flatMap(_[List[Any]]("path0").map(_.length)).get)
  }
}
