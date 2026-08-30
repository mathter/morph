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
package io.github.mathter.morph.processing.json

import io.github.mathter.morph.data.PathMap
import net.javacrumbs.jsonunit.assertj.JsonAssertions
import net.javacrumbs.jsonunit.core
import org.junit.jupiter.api.Test

class JsonSerializerTest {
  @Test
  def test(): Unit = {
    val jsonSerializer = new JsonSerializer()
    val pm = PathMap.empty

    pm("p0/01") = 10
    pm("p0/01") = 20
    pm("p0/02") = 30
    pm("p0") = 40

    JsonAssertions.assertThatJson(jsonSerializer.serialize(pm).stripMargin)
      .when(core.Option.IGNORING_ARRAY_ORDER)
      .isEqualTo(JsonAssertions.json(
        """{
          | "p0": [
          |   {
          |     "01" : [ 10, 20 ],
          |     "02" : 30
          |   },
          |   40
          | ]
          | }""".stripMargin
      ))
  }
}
