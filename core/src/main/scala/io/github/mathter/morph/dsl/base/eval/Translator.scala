package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.eval.{Eval, Terminal}

class Translator extends io.github.mathter.morph.eval.Translator {
  override def translate[T](terminal: Terminal): Eval[T] = terminal.asInstanceOf[Eval[T]]
}
