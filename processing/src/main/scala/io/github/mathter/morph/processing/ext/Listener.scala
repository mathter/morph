package io.github.mathter.morph.processing.ext

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path

trait Listener {
  def root: Path

  def related: Set[Path]

  def apply(pathMap: PathMap): Unit
}
