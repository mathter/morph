package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.dsl.base.{BaseContext, BaseDsl, Evaluator}
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{Arguments, MethodSource}

import java.util.stream

class BooleanOpsTest {
  @ParameterizedTest
  @MethodSource(Array("andTestArgs"))
  def andTest(x: Boolean, y: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x) and dsl.literal(y)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("andTestArgs"))
  def andOpTest(x: Boolean, y: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x) && dsl.literal(y)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("orTestArgs"))
  def orTest(x: Boolean, y: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x) or dsl.literal(y)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("orTestArgs"))
  def orOpTest(x: Boolean, y: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x) || dsl.literal(y)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("xorTestArgs"))
  def xorTest(x: Boolean, y: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x) xor dsl.literal(y)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("xorTestArgs"))
  def xorOpTest(x: Boolean, y: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x) ^ dsl.literal(y)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("notTestArgs"))
  def notTest(x: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = dsl.literal(x).not

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }

  @ParameterizedTest
  @MethodSource(Array("notTestArgs"))
  def notOpTest(x: Boolean, r: Boolean): Unit = {
    implicit val context: BaseContext = new BaseContext(PathMap.empty)
    val dsl: Dsl = BaseDsl()
    val s = !dsl.literal(x)

    Assertions.assertEquals(r, Evaluator.evalSource(s).get)
  }
}

object BooleanOpsTest {
  def andTestArgs(): stream.Stream[Arguments] = {
    stream.Stream.of(
      Arguments.of(false, false, false),
      Arguments.of(false, true, false),
      Arguments.of(true, false, false),
      Arguments.of(true, true, true)
    )
  }

  def orTestArgs(): stream.Stream[Arguments] = {
    stream.Stream.of(
      Arguments.of(false, false, false),
      Arguments.of(false, true, true),
      Arguments.of(true, false, true),
      Arguments.of(true, true, true)
    )
  }

  def xorTestArgs(): stream.Stream[Arguments] = {
    stream.Stream.of(
      Arguments.of(false, false, false),
      Arguments.of(false, true, true),
      Arguments.of(true, false, true),
      Arguments.of(true, true, false)
    )
  }

  def notTestArgs(): stream.Stream[Arguments] = {
    stream.Stream.of(
      Arguments.of(false, true),
      Arguments.of(false, true)
    )
  }
}