package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

implicit class NumericSourceOps[T](x: Source[T])(using num: Integral[T], classTag: ClassTag[T]) {

  import scala.math.Integral.Implicits.infixIntegralOps

  def +(y: Source[T]): Source[T] = {
    x.composite(y).fun((left, right) => left + right)
  }

  def -(y: Source[T]): Source[T] = {
    x.composite(y).fun((left, right) => left - right)
  }

  def *(y: Source[T]): Source[T] = {
    x.composite(y).fun((left, right) => left * right)
  }

  def /(y: Source[T]): Source[T] = {
    x.composite(y).fun((left, right) => left / right)
  }

  def %(y: Source[T]): Source[T] = {
    x.composite(y).fun((left, right) => left % right)
  }

  def abs: Source[T] = x.custom(_.abs)

  def negate: Source[T] = x.custom(-_)

  def sign: Source[T] = x.custom(_.sign)

  def /%(y: Source[T]): Source[(T, T)] =
    x.composite(y).fun((left, right) => (left / right, left % right))
}