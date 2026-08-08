package io.github.mathter.morph.dsl

import scala.reflect.ClassTag

/**
 * Numeric operations on `Source[T]` where `T` has an `Integral` instance.
 *
 * Provides arithmetic operators and helpers such as `abs`, `negate`, and
 * `sign`. Operations are implemented by composing sources and applying the
 * underlying numeric operation during evaluation.
 */
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