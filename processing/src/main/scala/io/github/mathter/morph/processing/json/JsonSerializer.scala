package io.github.mathter.morph.processing.json

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.processing.Serializer
import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.json.JsonMapper

class JsonSerializer(val objectMapper: ObjectMapper = JsonMapper.builder().build()) extends Serializer[String] {
  override def serialize(pathMap: PathMap): String = {

    val v = pathMap.toJavaMap(p => p.segment)
    this.objectMapper.writeValueAsString(pathMap.toJavaMap(p => p.segment))
  }
}