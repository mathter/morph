package io.github.mathter.morph.dsl

trait ResultDsl {
  def result[T]: Acceptor[T]

  def result[T](tag: Source[Any]): Acceptor[T]
}
