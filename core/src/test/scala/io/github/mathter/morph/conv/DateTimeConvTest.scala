package io.github.mathter.morph.conv

import org.junit.jupiter.api.{Assertions, Test}

class DateTimeConvTest {
  @Test
  def string2localDate(): Unit = {
    val origin = "2026-07-01"
    val converted = DateTimeConv.string2localDate(origin)
    val reverse = DateTimeConv.localDate2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2localDateTime(): Unit = {
    val origin = "2026-07-01T12:10:15"
    val converted = DateTimeConv.string2localDateTime(origin)
    val reverse = DateTimeConv.localDateTime2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2zonedDateTime(): Unit = {
    val origin = "2026-07-03T21:45:33.131983+03:00[Europe/Moscow]"
    val converted = DateTimeConv.string2zonedDateTime(origin)
    val reverse = DateTimeConv.zonedDateTime2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2offsetDateTime(): Unit = {
    val origin = "2026-07-03T21:49:22.857246+03:00"
    val converted = DateTimeConv.string2offsetDateTime(origin)
    val reverse = DateTimeConv.offsetDateTime2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2offsetDate(): Unit = {
    val origin = "2026-07-03-12:00"
    val converted = DateTimeConv.string2offsetDate(origin)
    val reverse = DateTimeConv.offsetDate2string(converted)

    Assertions.assertEquals(origin, reverse)
  }

  @Test
  def string2offsetTime(): Unit = {
    val origin = "21:49:22.857246+03:00"
    val converted = DateTimeConv.string2offsetTime(origin)
    val reverse = DateTimeConv.offsetTime2string(converted)

    Assertions.assertEquals(origin, reverse)
  }
}
