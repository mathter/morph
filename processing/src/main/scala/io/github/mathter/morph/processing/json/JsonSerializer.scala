package io.github.mathter.morph.processing.json

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.processing.Serializer
import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.json.JsonMapper

/**
 * JSON serializer implementation that converts a PathMap into its JSON
 * string representation.
 *
 * @param objectMapper Jackson ObjectMapper used for serialization. A
 *                     default JsonMapper is provided when none is
 *                     supplied.
 */
class JsonSerializer(val objectMapper: ObjectMapper = JsonMapper.builder().build()) extends Serializer[String] {
  /**
   * Serialize the given PathMap into a JSON string. The PathMap is
   * converted to a Java map using the path segment as keys before
   * delegating to Jackson.
   *
   * @param pathMap the parsed data to serialize
   * @return JSON string representation of the pathMap
   */
  override def serialize(pathMap: PathMap): String = {

    val v = pathMap.toJavaMap(p => p.segment)
    this.objectMapper.writeValueAsString(pathMap.toJavaMap(p => p.segment))
  }
}