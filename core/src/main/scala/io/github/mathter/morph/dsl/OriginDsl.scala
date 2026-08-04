package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap

trait OriginDsl {
  def origin: Source[PathMap]
}
