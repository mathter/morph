package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.eval.{Eval, Terminal}

private class Translator extends io.github.mathter.morph.eval.Translator {
  override def translate[T](terminal: Terminal): Eval[T] = terminal.asInstanceOf[Eval[T]]
}
