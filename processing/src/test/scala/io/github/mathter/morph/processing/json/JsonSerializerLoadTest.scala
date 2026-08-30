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

import io.github.mathter.morph.processing.xml.simple.ParserFactory
import org.junit.jupiter.api.Test

import scala.xml.InputSource

class JsonSerializerLoadTest {
  @Test
  def test(): Unit = {
    val is = new InputSource(classOf[JsonSerializerLoadTest].getClassLoader.getResourceAsStream("book.xml"))
    val pm = ParserFactory.newNSInstance().xmlParser.parse(is)
    val serializer = JsonSerializer()

    val start = System.nanoTime()
    for (i <- 0 to 1_000) {
      serializer.serialize(pm)
    }
    println((System.nanoTime() - start) / 1_000_000_000.0 / 1_000_000)
  }
}
