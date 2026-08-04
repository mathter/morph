package io.github.mathter.morph.conv

import org.junit.jupiter.api.{Assertions, Test}

class NumberConvTest {
  @Test
  def string2byte(): Unit = {
    val origin = "1"
    val converted = NumberConv.string2byte(origin)
    val reverse = NumberConv.byte2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2short(): Unit = {
    val origin = "1"
    val converted = NumberConv.string2short(origin)
    val reverse = NumberConv.short2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2int(): Unit = {
    val origin = "1"
    val converted = NumberConv.string2int(origin)
    val reverse = NumberConv.int2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2long(): Unit = {
    val origin = "1"
    val converted = NumberConv.string2long(origin)
    val reverse = NumberConv.long2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2float(): Unit = {
    val origin = "1.0"
    val converted = NumberConv.string2float(origin)
    val reverse = NumberConv.float2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2double(): Unit = {
    val origin = "1.0"
    val converted = NumberConv.string2double(origin)
    val reverse = NumberConv.double2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2bigInt(): Unit = {
    val origin = "12332141431214"
    val converted = NumberConv.string2bigInt(origin)
    val reverse = NumberConv.bigInt2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2bigDecimal(): Unit = {
    val origin = "12332141431214.984093274827482"
    val converted = NumberConv.string2bigDecimal(origin)
    val reverse = NumberConv.bigDecimal2string(converted)

    Assertions.assertEquals(origin, reverse)
  }
}
