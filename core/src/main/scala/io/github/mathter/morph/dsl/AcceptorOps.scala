package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.eval.Terminal
import io.github.mathter.morph.path.Path

/**
 * Convenience extension methods for `Acceptor[PathMap]` to improve ergonomics
 * when binding acceptors to specific `Path` locations.
 *
 * Example usage:
 *   obj.by(path).from(source)
 *
 * These helpers bridge the DSL and path-based data model.
 */
implicit class AcceptorOps(x: Acceptor[PathMap]) {
  /** Bind the acceptor to a path within the `PathMap`. */
  infix inline def by[T](path: Path): Acceptor[T] = x.dsl.by(x, path)

  /**
   * Shortcut to update `path` with `source` using this acceptor and obtain a
   * terminal expression.
   */
  inline def update[T](path: Path, source: Source[T]): Source[T] & Terminal =
    this.by(path).from(source)
}