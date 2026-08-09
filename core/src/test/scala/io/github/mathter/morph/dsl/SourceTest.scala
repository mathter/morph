package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.{Opt, PathMap}
import io.github.mathter.morph.dsl.base.BaseDsl
import io.github.mathter.morph.dsl.base.eval.{BaseContext, Evaluator}
import io.github.mathter.morph.eval.EvalException
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.{Assertions, Test}

class SourceTest {
  @Test
  def testIfEmptyEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.nothing[String]


    val d = s.ifEmpty({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(defaultValue, Evaluator.evalSource(d).get)
  }

  @Test
  def testIfEmptyNonEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(value)


    val d = s.ifEmpty({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(value, Evaluator.evalSource(d).get)
  }

  @Test
  def testIfNullNotNull(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(value)


    val d = s.ifNull({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(value, Evaluator.evalSource(d).get)
  }

  @Test
  def testIfNullNull(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.nil[String]


    val d = s.ifNull({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(defaultValue, Evaluator.evalSource(d).get)
  }

  @Test
  def testIfNullEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.nothing[String]


    val d = s.ifNull({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertTrue(Evaluator.evalSource(d).isEmpty)
  }

  @Test
  def testIfNullOrEmptyEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.nothing[String]


    val d = s.ifNullOrEmpty({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(defaultValue, Evaluator.evalSource(d).get)
  }

  @Test
  def testIfNullOrEmptyNull(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.nil[String]


    val d = s.ifNullOrEmpty({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(defaultValue, Evaluator.evalSource(d).get)
  }

  @Test
  def testIfNullOrEmptyNonNull(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val defaultValue = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(value)


    val d = s.ifNullOrEmpty({
      defaultValue
    })

    Assertions.assertNotNull(d)
    Assertions.assertEquals(value, Evaluator.evalSource(d).get)
  }

  @Test
  def testErrorIfNullNonNull(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(value).errorIfNull

    Assertions.assertNotNull(s)
    Assertions.assertEquals(Opt(value), Evaluator.evalSource(s))
  }

  @Test
  def testErrorIfNullNull(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s = dsl.nil[String].errorIfNull

    Assertions.assertNotNull(s)
    Assertions.assertThrows(classOf[EvalException], () => Evaluator.evalSource(s))
  }

  @Test
  def testErrorIfEmptyNonEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(value).errorIfEmpty

    Assertions.assertNotNull(s)
    Assertions.assertEquals(Opt(value), Evaluator.evalSource(s))
  }

  @Test
  def testErrorIfEmptyEmpty(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val s = dsl.nothing[String].errorIfEmpty

    Assertions.assertNotNull(s)
    Assertions.assertThrows(classOf[EvalException], () => Evaluator.evalSource(s))
  }
}
