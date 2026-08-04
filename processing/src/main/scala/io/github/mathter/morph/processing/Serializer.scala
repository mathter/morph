package io.github.mathter.morph.processing

import io.github.mathter.morph.data.PathMap

trait Serializer[T] {
  def serialize(pathMap: PathMap): T
}
