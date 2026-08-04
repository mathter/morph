package io.github.mathter.morph.eval

import io.github.mathter.morph.data.{Opt, PathMap}

trait Context {
  def origin: PathMap

  def target[T](tag: Any): Opt[T]

  def target[T](tag: Any, opt: Opt[T]): Opt[T]
}
