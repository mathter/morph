package io.github.mathter.morph.dsl

import io.github.mathter.morph.eval.Terminal

trait Acceptor[T] extends Source[T] {
  def from(source: Source[T]): Source[T] & Terminal
}
