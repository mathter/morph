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
package io.github.mathter.morph.data

import org.junit.jupiter.api.{Assertions, Test}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInput, ObjectInputStream, ObjectOutputStream}
import scala.util.Using

class InnerMapTest {
  @Test
  def testSerialization(): Unit = {
    val origin: InnerMap = new InnerMap(11)

    origin("path") = InnerMap.newQueue.appended("Hi")

    val readed = Using(new ByteArrayOutputStream()) {
      baos => {
        Using(new ObjectOutputStream(baos)) {
          oo =>
            oo.writeObject(origin)
            baos
        }
          .get
      }
    }
      .map(_.toByteArray)
      .map(new ByteArrayInputStream(_))
      .map(new ObjectInputStream(_))
      .map(oi => oi.readObject())
      .get

    Assertions.assertTrue(readed.isInstanceOf[InnerMap])

    val map = readed.asInstanceOf[InnerMap]
    Assertions.assertEquals(origin.id, map.id)
    Assertions.assertEquals(origin, map)
  }
}
