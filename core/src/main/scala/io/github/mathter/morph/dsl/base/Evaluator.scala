package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.Source
import io.github.mathter.morph.eval.{Context, Eval, Terminal}

object Evaluator {
  def evalSource[T](source: Source[T])(using context: Context): Opt[T] = {
    source.asInstanceOf[Eval[T]].eval
  }

  def eval[T](terminal: Terminal)(using context: Context): Opt[T] = {
    terminal.asInstanceOf[Eval[T]].eval
  }
}
