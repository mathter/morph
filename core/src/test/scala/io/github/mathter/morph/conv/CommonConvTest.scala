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
package io.github.mathter.morph.conv

import org.junit.jupiter.api.{Assertions, Test}

import java.util.UUID

class CommonConvTest {
  @Test
  def string2uuid(): Unit = {
    val origin = UUID.randomUUID()
    val converted = CommonConv.uuid2string(origin)
    val reversed = CommonConv.string2uuid(converted)

    Assertions.assertEquals(origin, reversed)
  }
}
