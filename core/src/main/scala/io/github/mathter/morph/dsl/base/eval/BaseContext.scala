package io.github.mathter.morph.dsl.base.eval

import io.github.mathter.morph.data.{Opt, PathMap}
import io.github.mathter.morph.eval.Context

import scala.collection.mutable

class BaseContext(val origin: PathMap,
                  val results: mutable.Map[Any, Opt[Any]] = mutable.HashMap.empty,
                  val cache: mutable.Map[Any, Opt[Any]] = mutable.HashMap.empty
                 ) extends Context {

  def this(origin: PathMap) = {
    this(origin, mutable.HashMap.empty, mutable.HashMap.empty)
  }

  override def target[T](tag: Any): Opt[T] =
    this.results.getOrElseUpdate(tag, Opt.empty[T]).asInstanceOf[Opt[T]]


  override def target[T](tag: Any, opt: Opt[T]): Opt[T] = {
    this.results.put(tag, opt)
    opt
  }
}
