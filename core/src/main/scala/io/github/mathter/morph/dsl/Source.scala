package io.github.mathter.morph.dsl

import io.github.mathter.morph.data.Opt

trait Source[T] {
  def dsl: Dsl

  def maps[D, DS <: Source[D]](f: Source[T] => Source[D]): DS

  def map[D, DS <: Source[D]](f: T => D): DS

  infix def customOpt[D](f: Opt[T] => Opt[D]): Source[D]

  infix def custom[D](f: T => D): Source[D]

  infix def composite[T0](source: Source[T0]): Composite[T, T0]

  def as[D]: Source[D]

  infix def equalsTo(another: Source[T]): Source[Boolean]

  def ==(another: Source[T]): Source[Boolean] = this.equalsTo(another)

  def pure: Boolean

  def pure(pure: Boolean): Source[T]

  def ifNull(default: => T): Source[T]

  def ifEmpty(default: => T): Source[T]

  def ifNullOrEmpty(default: => T): Source[T]

  def errorIfNull: Source[T]

  def errorIfEmpty: Source[T]

  def errorIfNullOrEmpty: Source[T]
}