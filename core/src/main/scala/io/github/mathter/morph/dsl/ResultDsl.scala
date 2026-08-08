package io.github.mathter.morph.dsl

/**
 * Helpers to create result acceptors used for final validation or extraction of
 * computed values. Optionally accepts a `tag` source to annotate or identify
 * the result in downstream processing.
 */
trait ResultDsl {
  /** Create a generic result acceptor. */
  def result[T]: Acceptor[T]

  /** Create a result acceptor annotated with `tag`. */
  def result[T](tag: Source[Any]): Acceptor[T]
}
