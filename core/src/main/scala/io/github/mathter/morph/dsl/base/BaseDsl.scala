package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.{Opt, PathMap}
import io.github.mathter.morph.dsl.base.BaseDsl.DEFAULT_DESTINATION_TAG
import io.github.mathter.morph.dsl.{Acceptor, Dsl, Group, Source, When}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}
import io.github.mathter.morph.path.Path

class BaseDsl extends Dsl {
  implicit private val dsl: Dsl = this

  override def origin: Source[PathMap] = {
    implicit val tracer = Tracer.trace3()
    new OriginSourceEval()
  }

  override def result[T]: Acceptor[T] = {
    implicit val tracer = Tracer.trace3()
    this.result(this.literal(DEFAULT_DESTINATION_TAG))
  }

  override def result[T](tag: Source[Any]): Acceptor[T] = {
    implicit val tracer = Tracer.trace3()
    new ResultEval[T](tag.asInstanceOf[Eval[Any]])
  }

  override def obj: Acceptor[PathMap] = {
    implicit val tracer = Tracer.trace3()
    new PathMapAcceptor()
  }

  override def literal[T](x: => T): Source[T] = {
    implicit val tracer = Tracer.trace3()
    new CalculatedLiteralEval[T](x)
  }

  override def nothing[T]: Source[T] = {
    implicit val tracer = Tracer.trace3()
    new NothingEval[T]
  }

  override def nil[T]: Source[T] = {
    implicit val tracer = Tracer.trace3()
    new CalculatedLiteralEval[T](null.asInstanceOf[T])
  }

  override def fls: Source[Boolean] = {
    implicit val tracer = Tracer.trace3()
    this.literal(false)
  }

  override def tr: Source[Boolean] = {
    implicit val tracer = Tracer.trace3()
    this.literal(true)
  }

  def first[T](source: Source[List[T]]): Source[T] = {
    implicit val tracer = Tracer.trace3()
    new ListElementByIndexEval[T](source.asInstanceOf[Eval[List[T]]], new AbstractEval[Int]() {
      override def evalI(context: Context): Opt[Int] = Opt(0)
    })
  }

  def last[T](source: Source[List[T]]): Source[T] = {
    implicit val tracer = Tracer.trace3()
    new ListElementByIndexEval[T](source.asInstanceOf[Eval[List[T]]], new AbstractEval[Int]() {
      override def evalI(implicit context: Context): Opt[Int] = source.asInstanceOf[Eval[List[T]]].eval.map(_.length - 1)
    })
  }

  def index[T](source: Source[List[T]], index: Source[Int]): Source[T] = {
    implicit val tracer = Tracer.trace3()
    new ListElementByIndexEval[T](source.asInstanceOf[Eval[List[T]]], index.asInstanceOf[Eval[Int]])
  }

  override def by[T](source: Source[PathMap], path: Path): Source[T] = {
    implicit val tracer = Tracer.trace3()
    new ByEval[T](source.asInstanceOf[Eval[PathMap]], path)
  }

  override def by[T](source: Acceptor[PathMap], path: Path): Acceptor[T] = {
    implicit val tracer = Tracer.trace3()
    new PathMapByPathAcceptor[T](source.asInstanceOf[Eval[PathMap]], path)
  }

  override def mapElem[T, D](source: Source[List[T]], f: T => D): Source[List[D]] = {
    implicit val tracer = Tracer.trace3()
    new MapElemEval[T, D](source.asInstanceOf[Eval[List[T]]], f)
  }

  override def mapsElem[T, D](source: Source[List[T]], f: Source[T] => Source[D]): Source[List[D]] = {
    implicit val tracer = Tracer.trace3()
    new MapsElemEval[T, D](source.asInstanceOf[Eval[List[T]]], f)
  }

  override def when[T](condition: Source[Boolean]): When[T] = {
    implicit val tracer = Tracer.trace3()
    new WhenEval[T](condition.asInstanceOf[Eval[Boolean]])
  }

  override def group[K, E](source: Source[List[E]], key: Source[E] => Source[K]): Group[K, E] = {
    implicit val tracer = Tracer.trace3()
    new GroupEval[K, E](source.asInstanceOf[Eval[List[E]]], key)
  }

  override def filter[T](source: Source[List[T]], p: Source[T] => Source[Boolean]): Source[List[T]] = {
    implicit val tracer = Tracer.trace3()
    new FilterEval[T](source.asInstanceOf[Eval[List[T]]], p)
  }

  override def distinctBy[K, T](source: Source[List[T]], key: Source[T] => Source[K]): Source[List[T]] = {
    implicit val tracer = Tracer.trace3()
    new DistinctEval[K, T](source.asInstanceOf[Eval[List[T]]], key)
  }
}

object BaseDsl {
  private val DEFAULT_DESTINATION_TAG: String = "<<default>>"

  implicit def baseDsl: BaseDsl = new BaseDsl
}

given [T]: Conversion[Source[T], Eval[T]] with {
  override def apply(x: Source[T]): Eval[T] = x.asInstanceOf[Eval[T]]
}