package io.github.mathter.morph.dsl

trait Dsl
  extends LiteralDsl
    with ListDsl
    with OriginDsl
    with ResultDsl
    with PathMapDsl
    with ConstDsl {

  def nothing[T]: Source[T]

  def unit[T](source: Source[T]): Source[T] = source

  def `if`[T](condition: Source[Boolean]): If[T]
}

given [T](using dsl: Dsl): Conversion[T, Source[T]] with {
  override def apply(x: T): Source[T] = dsl.literal(x)
}