package io.github.mathter.morph.eval

trait Translator {
  def translate[T](terminal: Terminal): Eval[T]
}
