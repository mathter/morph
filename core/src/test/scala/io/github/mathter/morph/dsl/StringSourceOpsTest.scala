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
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.{Assertions, Test}
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{Arguments, ArgumentsProvider, ArgumentsSource, MethodSource}
import org.junit.jupiter.params.support.ParameterDeclarations

import java.util.stream.Stream

class StringSourceOpsTest {
  @Test
  def testUpperCase(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal("Hello").toUpperCase

    Assertions.assertEquals("HELLO", Evaluator.evalSource(s).get)
  }

  @Test
  def testLowerCase(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal("Hello").toLowerCase

    Assertions.assertEquals("hello", Evaluator.evalSource(s).get)
  }

  @Test
  def testReplaceAll(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal("Hello").replaceAll("^.*(ll).*$", "$1")

    Assertions.assertEquals("ll", Evaluator.evalSource(s).get)

    val a: String => String = s => s.replaceAll("^.*(ll).*$", "$1")
    val s0 = dsl.literal("Hello").custom(a)

    Assertions.assertEquals("ll", Evaluator.evalSource(s0).get)
  }

  @Test
  def testLength(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val origin = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(origin).length

    Assertions.assertNotNull(s)
    Assertions.assertEquals(origin.length, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @ArgumentsSource(classOf[StringSourceOpsTest.TestIsEmpty])
  def testIsEmpty(origin: String, expected: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s = dsl.literal(origin).isEmpty

    Assertions.assertNotNull(s)
    Assertions.assertEquals(expected, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @ArgumentsSource(classOf[StringSourceOpsTest.TestNotEmpty])
  def testNonEmpty(origin: String, expected: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s = dsl.literal(origin).nonEmpty

    Assertions.assertNotNull(s)
    Assertions.assertEquals(expected, Evaluator.evalSource(s).get)
  }

  @Test
  def testMatches(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val origin = "This is a simple string"
    val s = dsl.literal(origin).matches("^This is.*$")

    Assertions.assertNotNull(s)
    Assertions.assertTrue(Evaluator.evalSource(s).get)
  }

  @Test
  def testMatches1(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s = dsl.nothing[String].matches("^This is.*$")

    Assertions.assertNotNull(s)
    Assertions.assertTrue(Evaluator.evalSource(s).isEmpty)
  }
}

object StringSourceOpsTest {
  class TestIsEmpty extends ArgumentsProvider {
    override def provideArguments(parameters: ParameterDeclarations, context: ExtensionContext): Stream[_ <: Arguments] =
      Stream.of(
        Arguments.of(RandomStringUtils.insecure().nextAlphabetic(10), false),
        Arguments.of("", true),
        Arguments.of(null, true),
      )
  }

  class TestNotEmpty extends ArgumentsProvider {
    override def provideArguments(parameters: ParameterDeclarations, context: ExtensionContext): Stream[_ <: Arguments] =
      Stream.of(
        Arguments.of(RandomStringUtils.insecure().nextAlphabetic(10), true),
        Arguments.of("", false),
        Arguments.of(null, false),
      )
  }
}