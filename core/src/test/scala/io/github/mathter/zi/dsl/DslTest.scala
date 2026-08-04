package io.github.mathter.zi.dsl

import io.github.mathter.zi.data.{Opt, PathMap}
import io.github.mathter.zi.dsl.base.BaseDsl
import io.github.mathter.zi.dsl.base.eval.{BaseContext, Evaluator}
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.{Assertions, Test}

import java.time.Instant

class DslTest {
  @Test
  def testResult(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val a: Acceptor[String] = dsl.result
    Assertions.assertNotNull(a)

    var o = Evaluator.evalSource(a)
    Assertions.assertNotNull(o)
    Assertions.assertTrue(o.isEmpty)

    val s = a.from("hello")
    o = Evaluator.eval(s)
    Assertions.assertNotNull(o)
    Assertions.assertEquals("hello", o.get)
  }

  @Test
  def testObj(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()

    val a = dsl.obj
    Assertions.assertNotNull(a)
    val v = Evaluator.evalSource(a)
    Assertions.assertNotNull(v)
    Assertions.assertTrue(v.isDefined)

    val f: Acceptor[String] = a.by("p0")
    Assertions.assertNotNull(f)
    val fv = Evaluator.evalSource(f)
    Assertions.assertNotNull(fv)
    Assertions.assertTrue(fv.isEmpty)

    val t = f.from("Hello")
    Assertions.assertNotNull(t)
    val fv2 = Evaluator.eval(t)
    Assertions.assertNotNull(fv2)
    Assertions.assertEquals("Hello", fv2.get)
  }

  @Test
  def literalTest(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val s = dsl.literal(value)

    Assertions.assertEquals(Opt(value), Evaluator.evalSource(s))
  }

  @Test
  def testNothing(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.nothing[String]

    Assertions.assertEquals(Opt.empty, Evaluator.evalSource(s))
  }

  @Test
  def testNil(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.nil[String]

    Assertions.assertEquals(Opt(null), Evaluator.evalSource(s))
  }

  @Test
  def testCustomOptArgInt(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s0 = dsl.literal(1)
    val s1 = s0.customOpt(option => option.map(_ + 1))

    Assertions.assertEquals(Opt(2), Evaluator.evalSource(s1))
  }

  @Test
  def testCustomOptArgNohting(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s0: Source[Int] = dsl.nothing
    val s1 = s0.customOpt(option => option.map(_ + 1))

    Assertions.assertEquals(Opt.empty, Evaluator.evalSource(s1))
  }

  @Test
  def testCustom(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val value = RandomStringUtils.insecure().nextAlphabetic(10)
    val s: Source[String] = dsl.literal(value).custom(s => s.toUpperCase)

    Assertions.assertEquals(value.toUpperCase, Evaluator.evalSource(s).get)
  }

  @Test
  def testComposite(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val value0 = RandomStringUtils.insecure().nextAlphabetic(10)
    val value1 = RandomStringUtils.insecure().nextAlphabetic(10)
    val s: Source[String] = dsl.literal(value0).composite(dsl.literal(value1)).fun(_ + _)

    Assertions.assertEquals(value0 + value1, Evaluator.evalSource(s).get)
  }

  @Test
  def testComposite1(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val value0 = RandomStringUtils.insecure().nextAlphabetic(10)
    val value1 = RandomStringUtils.insecure().nextAlphabetic(20)
    val value2 = RandomStringUtils.insecure().nextAlphabetic(30)
    val s: Source[String] = dsl
      .literal(value0)
      .composite(dsl.literal(value1))
      .composite(dsl.literal(value2)).fun(_ + _ + _)

    Assertions.assertEquals(value0 + value1 + value2, Evaluator.evalSource(s).get)
  }

  @Test
  def testComposite2(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val value0 = RandomStringUtils.insecure().nextAlphabetic(10)
    val value1 = RandomStringUtils.insecure().nextAlphabetic(20)
    val value2 = RandomStringUtils.insecure().nextAlphabetic(30)
    val value3 = RandomStringUtils.insecure().nextAlphabetic(40)
    val s: Source[String] = dsl
      .literal(value0)
      .composite(dsl.literal(value1))
      .composite(dsl.literal(value2))
      .composite(dsl.literal(value3))
      .fun(_ + _ + _ + _)

    Assertions.assertEquals(value0 + value1 + value2 + value3, Evaluator.evalSource(s).get)
  }

  @Test
  def testComposite3(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val value0 = RandomStringUtils.insecure().nextAlphabetic(10)
    val value1 = RandomStringUtils.insecure().nextAlphabetic(20)
    val value2 = RandomStringUtils.insecure().nextAlphabetic(30)
    val value3 = RandomStringUtils.insecure().nextAlphabetic(40)
    val value4 = RandomStringUtils.insecure().nextAlphabetic(40)
    val s: Source[String] = dsl
      .literal(value0)
      .composite(dsl.literal(value1))
      .composite(dsl.literal(value2))
      .composite(dsl.literal(value3))
      .composite(dsl.literal(value4))
      .fun(_ + _ + _ + _ + _)

    Assertions.assertEquals(value0 + value1 + value2 + value3 + value4, Evaluator.evalSource(s).get)
  }

  @Test
  def testFalse(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.fls
    val v = Evaluator.evalSource(s)
    Assertions.assertFalse(v.get)
  }

  @Test
  def testTrue(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.tr
    val v = Evaluator.evalSource(s)
    Assertions.assertTrue(v.get)
  }

  @Test
  def testAs(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(10).as[BigInt]

    Assertions.assertEquals(10, Evaluator.evalSource(s).get)
  }

  @Test
  def testIf0(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.`if`(dsl.tr).Then(dsl.literal("Then"))

    Assertions.assertEquals("Then", Evaluator.evalSource(s).get)
  }

  @Test
  def testIf1(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.`if`(dsl.tr).Then(dsl.literal("Then")).Else(dsl.literal("Else"))

    Assertions.assertEquals("Then", Evaluator.evalSource(s).get)
  }

  @Test
  def testIf2(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.`if`(dsl.fls).Then(dsl.literal("Then")).Else(dsl.literal("Else"))

    Assertions.assertEquals("Else", Evaluator.evalSource(s).get)
  }

  @Test
  def testIf3(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.`if`(dsl.fls).Then(dsl.literal("Then")).Else(dsl.literal("Else"))
      .If(dsl.tr).Then(dsl.literal("Then2"))

    Assertions.assertEquals("Then2", Evaluator.evalSource(s).get)
  }

  @Test
  def testIf4(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.`if`(dsl.fls).Then(dsl.literal("Then")).Else(dsl.literal("Else"))
      .If(dsl.fls).Then(dsl.literal("Then2")).Else(dsl.literal("Else2"))

    Assertions.assertEquals("Else2", Evaluator.evalSource(s).get)
  }

  @Test
  def testPure(): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    implicit val dsl: Dsl = BaseDsl()
    val value = Instant.now()
    val s = dsl.literal(value).pure(true)

    Assertions.assertNotNull(s)
    Assertions.assertEquals(value, Evaluator.evalSource(s).get)
  }
}
