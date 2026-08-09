package io.github.mathter.morph.processing

import io.github.mathter.morph.data.PathMap

/**
 * Serializer abstraction for converting a PathMap into a target
 * representation.
 *
 * @tparam T resulting serialized type (for example String or XML DOM)
 */
trait Serializer[T] {
  /**
   * Serialize the provided PathMap into an instance of T.
   *
   * @param pathMap the PathMap containing parsed data
   * @return serialized representation of the input
   */
  def serialize(pathMap: PathMap): T
}
