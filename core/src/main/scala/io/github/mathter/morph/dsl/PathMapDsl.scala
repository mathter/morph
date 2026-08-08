package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path

/**
 * Helpers for working with `PathMap` sources and acceptors.
 *
 * `obj` provides an acceptor rooted at the origin `PathMap`. The `by` helpers
 * enable selecting nested values from a `PathMap` source or binding an
 * `Acceptor[PathMap]` to a nested path producing an `Acceptor[T]`.
 */
trait PathMapDsl {
  /** An acceptor rooted at the `PathMap` origin (convenience factory). */
  def obj: Acceptor[PathMap]

  /** Read a value from a `PathMap` source by `path`. */
  def by[T](source: Source[PathMap], path: Path): Source[T]

  /** Bind an `Acceptor[PathMap]` to `path` producing an `Acceptor[T]`. */
  def by[T](source: Acceptor[PathMap], path: Path): Acceptor[T]
}
