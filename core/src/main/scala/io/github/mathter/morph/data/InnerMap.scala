package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path

import scala.collection.generic.DefaultSerializationProxy
import scala.collection.{Factory, mutable}

private class InnerMap extends mutable.LinkedHashMap[Path, mutable.Queue[Any]] with Serializable {
  protected override def writeReplace(): AnyRef = new DefaultSerializationProxy(new DeserializationFactory, this)

  private class DeserializationFactory extends Factory[(Path, mutable.Queue[Any]), InnerMap] with Serializable {
    override def fromSpecific(it: IterableOnce[(Path, mutable.Queue[Any])]): InnerMap = new InnerMap().addAll(it)

    override def newBuilder: mutable.Builder[(Path, mutable.Queue[Any]), InnerMap] =
      new mutable.GrowableBuilder(new InnerMap())
  }
}

private object InnerMap {
  def newQueue: mutable.Queue[Any] = mutable.Queue.empty
}