package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.dsl.Source
import io.github.mathter.morph.path.Path

/**
 * Extension helpers for `Source[PathMap]` enabling convenient `by(path)`
 * traversal to fetch nested values from a `PathMap` source.
 */
implicit class PathMapSourceOps(val x: Source[PathMap]) {
  /** Retrieve a value from the path map source by the provided `Path`. */
  def by[T](path: Path): Source[T] = x.dsl.by(x, path)
}