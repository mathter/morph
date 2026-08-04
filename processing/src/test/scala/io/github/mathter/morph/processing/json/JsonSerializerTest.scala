package io.github.mathter.morph.processing.json

import io.github.mathter.morph.data.PathMap
import net.javacrumbs.jsonunit.assertj.JsonAssertions
import net.javacrumbs.jsonunit.core
import org.junit.jupiter.api.Test

class JsonSerializerTest {
  @Test
  def test(): Unit = {
    val jsonSerializer = new JsonSerializer()
    val pm = PathMap.empty

    pm("p0/01") = 10
    pm("p0/01") = 20
    pm("p0/02") = 30
    pm("p0") = 40

    JsonAssertions.assertThatJson(jsonSerializer.serialize(pm).stripMargin)
      .when(core.Option.IGNORING_ARRAY_ORDER)
      .isEqualTo(JsonAssertions.json(
        """{
          | "p0": [
          |   {
          |     "01" : [ 10, 20 ],
          |     "02" : 30
          |   },
          |   40
          | ]
          | }""".stripMargin
      ))
  }
}
