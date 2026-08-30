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
package io.github.mathter.morph.path

import scala.util.Random

object LoadTest {
  def main(args: Array[String]): Unit = {
    val names: List[String] = Range(0, 10000, 1).map(i => Random.nextInt().toString).toList
    val s = Range(0, 1_000_000, 1)
    val start = System.currentTimeMillis()
    for (i <- s) {
      val p = Path(names(Random.nextInt(names.length)))
    }

    println((System.currentTimeMillis() - start) / 1000.0)
  }
}
