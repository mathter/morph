package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap

/**
 * Provides the origin `PathMap` source representing the root data against
 * which expressions are evaluated.
 */
trait OriginDsl {
  /** The source that yields the origin `PathMap` instance for evaluation. */
  def origin: Source[PathMap]
}
