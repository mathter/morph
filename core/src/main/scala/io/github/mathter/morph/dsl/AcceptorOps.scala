package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.eval.Terminal
import io.github.mathter.morph.path.Path

implicit class AcceptorOps(x: Acceptor[PathMap]) {
  infix inline def by[T](path: Path): Acceptor[T] = x.dsl.by(x, path)

  inline def update[T](path: Path, source: Source[T]): Source[T] & Terminal =
    this.by(path).from(source)
}