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

class ListSourceOpsTest {
  @Test
  def testFirst(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(1, 2, 3)).first
    val v = Evaluator.evalSource(s)

    Assertions.assertEquals(1, Evaluator.evalSource(s).get)
  }

  @Test
  def testLast(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(1, 2, 3)).last
    val v = Evaluator.evalSource(s)

    Assertions.assertEquals(3, Evaluator.evalSource(s).get)
  }

  @Test
  def testIndex(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(1, 2, 3, 4, 5)).index(dsl.literal(2))

    Assertions.assertEquals(3, Evaluator.evalSource(s).get)
  }

  @Test
  def testMapElem(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(1, 2, 3)).mapElem(_ + 1)

    Assertions.assertEquals(List(2, 3, 4), Evaluator.evalSource(s).get)
  }

  @Test
  def testMapsElem(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(1, 2, 3)).mapsElem(e => e + dsl.literal(1))

    Assertions.assertEquals(List(2, 3, 4), Evaluator.evalSource(s).get)
  }

  @Test
  def testGroup(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(0, 1, 2, 3, 4, 5, 6, 7))
      .group(s => s % dsl.literal(2))

    val result = Evaluator.evalSource(s)
    Assertions.assertNotNull(result)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals(2, result.get.size)
    Assertions.assertEquals(0, result.get.toList(0)._1)
    Assertions.assertEquals(List(0, 2, 4, 6), result.get.toList(0)._2)
    Assertions.assertEquals(1, result.get.toList(1)._1)
    Assertions.assertEquals(List(1, 3, 5, 7), result.get.toList(1)._2)
  }

  @Test
  def testApplyGroup(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(0, 1, 2, 3, 4, 5, 6, 7))
      .group(s => s % dsl.literal(2))
      .apply((l: Source[Int], r: Source[List[Int]]) => r.custom(_.sum))

    val result = Evaluator.evalSource(s)
    Assertions.assertNotNull(result)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals(2, result.get.size)
    Assertions.assertEquals(12, result.get(0))
    Assertions.assertEquals(16, result.get(1))
  }

  @Test
  def testFilter(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s = dsl.literal(List(0, 1, 2, 3, 4, 5, 6, 7)).filter(s => (s % 2) equalsTo 0)

    val result = Evaluator.evalSource(s)
    Assertions.assertNotNull(result)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals(List(0, 2, 4, 6), result.get)
  }

  @Test
  def testDistinct(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s: Source[List[Int]] = dsl.literal(List(0, 1, 2, 1, 3, 3, 4, 2, 5, 6, 7)).distinct

    val result = Evaluator.evalSource(s)
    Assertions.assertNotNull(result)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals(List(0, 1, 2, 3, 4, 5, 6, 7), result.get)
  }

  @Test
  def testDistinctBy(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s: Source[List[Int]] = dsl.literal(List(0, 1, 2, 1, 3, 3, 4, 2, 5, 6, 7)).distinctBy(s => s % 2)

    val result = Evaluator.evalSource(s)
    Assertions.assertNotNull(result)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals(List(0, 1), result.get)
  }

  @Test
  def testDistinctByEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s: Source[List[Int]] = dsl.literal(List(0, 1, 2, 1, 3, 3, 4, 2, 5, 6, 7)).distinctBy(s => dsl.nothing)

    val result = Evaluator.evalSource(s)
    Assertions.assertNotNull(result)
    Assertions.assertTrue(result.isDefined)
    Assertions.assertEquals(List(0), result.get)
  }
}
