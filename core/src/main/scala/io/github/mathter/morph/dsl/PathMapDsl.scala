package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path

trait PathMapDsl {
  def obj: Acceptor[PathMap]

  def by[T](source: Source[PathMap], path: Path): Source[T]

  def by[T](source: Acceptor[PathMap], path: Path): Acceptor[T]
}
