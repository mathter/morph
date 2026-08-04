package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.dsl.Source
import io.github.mathter.morph.path.Path

implicit class PathMapSourceOps(val x: Source[PathMap]) {
  def by[T](path: Path): Source[T] = x.dsl.by(x, path)
}