package io.github.mathter.morph.dsl

trait Group[K, E] extends Source[List[(K, List[E])]] {
  def apply[D](f: (Source[K], Source[List[E]]) => Source[D]): Source[List[D]]
}
