package io.github.mathter.morph.conv

import org.junit.jupiter.api.{Assertions, Test}

import java.util.UUID

class CommonConvTest {
  @Test
  def string2uuid(): Unit = {
    val origin = UUID.randomUUID()
    val converted = CommonConv.uuid2string(origin)
    val reversed = CommonConv.string2uuid(converted)

    Assertions.assertEquals(origin, reversed)
  }
}
