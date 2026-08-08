package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path
import org.apache.commons.lang3.tuple.Pair

import java.util
import scala.jdk.CollectionConverters.*

trait PathMap {
  def apply[T](path: Path): Opt[T]

  def get[T](path: Path): Opt[T] = this.apply(path)

  def iget[T](path: Path): Opt[T]

  def put[T](path: Path, value: T): Unit = update(path, value)

  def update[T](path: Path, value: T): Unit

  def keys: Set[Path]

  def jkeys: util.Set[Path] = this.keys.asJava

  def entries: List[(Path, ?)]

  def jentries: util.List[Pair[Path, ?]] = this.entries.map(e => Pair.of(e._1, e._2)).asJava

  def toMap[K](f: Path => K): collection.Map[K, Any]

  def toJavaMap[K](f: Path => K): util.Map[K, Object]

  def asJava: JPathMap

  def asScala: PathMap
}

object PathMap {
  def empty: PathMap = {
    EPathMap()
  }

  def jempty: JPathMap = {
    JEPathMap()
  }
}