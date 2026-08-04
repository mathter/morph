package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path

import java.util

trait PathMap {
  def apply[T](path: Path): Opt[T]

  def get[T](path: Path): Opt[T] = this.apply(path)

  def iget[T](path: Path): Opt[T]

  def put[T](path: Path, value: T): Unit = update(path, value)

  def update[T](path: Path, value: T): Unit

  def keys: Set[Path]

  def entries: List[(Path, ?)]

  def toMap[K](f: Path => K): collection.Map[K, Any]

  def toJavaMap[K](f: Path => K): util.Map[K, Object]
}

object PathMap {
  def empty: PathMap = {
    EPathMap()
  }
}