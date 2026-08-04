package io.github.mathter.morph.dsl

trait ListDsl {
  def first[T](source: Source[List[T]]): Source[T]

  def last[T](source: Source[List[T]]): Source[T]

  def index[T](source: Source[List[T]], index: Source[Int]): Source[T]

  def group[K, E](source: Source[List[E]], key: Source[E] => Source[K]): Group[K, E]

  def filter[T](source: Source[List[T]], p: Source[T] => Source[Boolean]): Source[List[T]]

  def distinctBy[K, T](source: Source[List[T]], key: Source[T] => Source[K]): Source[List[T]]

  def mapElem[T, D](source: Source[List[T]], f: T => D): Source[List[D]]

  def mapsElem[T, D](source: Source[List[T]], f: Source[T] => Source[D]): Source[List[D]]
}
