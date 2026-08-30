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

class NumericSourceOpsTest {
  @Test
  def intPlus(): Unit = {
    val leftOrigin = 10
    val rightOrigin = 20
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val left = dsl.literal(leftOrigin)
    val right = dsl.literal(rightOrigin)
    val s = left + right

    Assertions.assertNotNull(left)
    Assertions.assertNotNull(right)
    Assertions.assertNotNull(s)

    Assertions.assertEquals(leftOrigin + rightOrigin, Evaluator.evalSource(s).get)
  }

  @Test
  def intMinus(): Unit = {
    val leftOrigin = 10
    val rightOrigin = 20
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val left = dsl.literal(leftOrigin)
    val right = dsl.literal(rightOrigin)
    val s = left - right

    Assertions.assertNotNull(left)
    Assertions.assertNotNull(right)
    Assertions.assertNotNull(s)

    Assertions.assertEquals(leftOrigin - rightOrigin, Evaluator.evalSource(s).get)
  }

  @Test
  def intMultiply(): Unit = {
    val leftOrigin = 10
    val rightOrigin = 20
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val left = dsl.literal(leftOrigin)
    val right = dsl.literal(rightOrigin)
    val s = left * right

    Assertions.assertNotNull(left)
    Assertions.assertNotNull(right)
    Assertions.assertNotNull(s)

    Assertions.assertEquals(leftOrigin * rightOrigin, Evaluator.evalSource(s).get)
  }

  @Test
  def intDivide(): Unit = {
    val leftOrigin = 30
    val rightOrigin = 20
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val left = dsl.literal(leftOrigin)
    val right = dsl.literal(rightOrigin)
    val s = left / right

    Assertions.assertNotNull(left)
    Assertions.assertNotNull(right)
    Assertions.assertNotNull(s)

    Assertions.assertEquals(leftOrigin / rightOrigin, Evaluator.evalSource(s).get)
  }

  @Test
  def intAbs(): Unit = {
    val origin = -30
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val s = dsl.literal(origin).abs

    Assertions.assertNotNull(s)
    Assertions.assertEquals(origin.abs, Evaluator.evalSource(s).get)
  }

  @Test
  def intNegate(): Unit = {
    val origin = -30
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val s = dsl.literal(origin).negate

    Assertions.assertNotNull(s)
    Assertions.assertEquals(-origin, Evaluator.evalSource(s).get)
  }

  @Test
  def intDivRem(): Unit = {
    import scala.math.Integral.Implicits.infixIntegralOps

    val leftOrigin = 30
    val rightOrigin = 20
    implicit val context = new BaseContext(PathMap.empty)
    implicit val dsl = new BaseDsl

    val left = dsl.literal(leftOrigin)
    val right = dsl.literal(rightOrigin)
    val s = left /% right

    Assertions.assertNotNull(left)
    Assertions.assertNotNull(right)
    Assertions.assertNotNull(s)

    Assertions.assertEquals(leftOrigin /% rightOrigin, Evaluator.evalSource(s).get)
  }
}
