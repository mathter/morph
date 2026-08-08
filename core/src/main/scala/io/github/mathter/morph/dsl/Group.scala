package io.github.mathter.morph.dsl

/**
 * Represents a grouped collection resulting from `ListDsl.group`.
 *
 * A `Group[K,E]` produces a `List[(K, List[E])]` where each tuple contains the
 * group key and the list of elements belonging to that key. The `apply` method
 * allows mapping each group to a derived value using a function that receives
 * the group key and the group's element-list as `Source` instances.
 */
trait Group[K, E] extends Source[List[(K, List[E])]] {
  def apply[D](f: (Source[K], Source[List[E]]) => Source[D]): Source[List[D]]
}
