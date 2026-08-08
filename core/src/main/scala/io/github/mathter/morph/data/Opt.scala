package io.github.mathter.morph.data

/**
 * An optional value container representing either a value of type `A` (wrapped in `Some`) or no value (`None`).
 *
 * `Opt` is similar to Scala's standard `Option` but provides a lightweight alternative with optimized performance.
 * It is covariant in its type parameter and provides monadic operations for functional programming patterns.
 *
 * The two implementations are:
 *  - `Some(value)` - contains a value of type `A`
 *  - `None` - represents the absence of a value
 *
 * @tparam A the type of the contained value
 * @example
 * {{{
 * val opt1 = Opt(42)           // Some(42)
 * val opt2 = Opt.empty[Int]    // None
 * val mapped = opt1.map(_ * 2) // Some(84)
 * val result = opt1.getOrElse(0) // 42
 * }}}
 */
sealed abstract class Opt[+A] extends IterableOnce[A] with Product with Serializable {

  import Opt.{None, Some}

  /**
   * Returns the contained value.
   *
   * @return the value contained in this `Opt`
   * @throws NoSuchElementException if this `Opt` is empty
   */
  def get: A

  /**
   * Returns an iterator over the value in this `Opt`.
   *
   * @return an empty iterator if this `Opt` is empty, or a single-element iterator if defined
   */
  override def iterator: Iterator[A] = {
    if (isEmpty) collection.Iterator.empty else collection.Iterator.single(this.get)
  }

  /**
   * Returns the known size of this `Opt`.
   *
   * @return 0 if empty, 1 if defined
   */
  override def knownSize: Int = if (isEmpty) 0 else 1

  /**
   * Checks if this `Opt` is empty.
   *
   * @return `true` if this `Opt` contains no value, `false` otherwise
   */
  final def isEmpty: Boolean = this eq None

  /**
   * Checks if this `Opt` is not empty (contains a value).
   *
   * Alias for [[isDefined]].
   *
   * @return `true` if this `Opt` contains a value, `false` otherwise
   */
  final def notEmpty: Boolean = isDefined

  /**
   * Checks if this `Opt` contains a value.
   *
   * @return `true` if this `Opt` contains a value, `false` if empty
   */
  final def isDefined: Boolean = !isEmpty

  /**
   * Checks if this `Opt` contains a value.
   *
   * Java-style method name alternative for [[isDefined]].
   *
   * @return `true` if this `Opt` contains a value, `false` if empty
   */
  final def isPresent(): Boolean = !isEmpty

  /**
   * Tests if this `Opt` contains the specified element.
   *
   * @param elem the element to check for equality
   * @tparam A1 a supertype of `A`
   * @return `true` if this `Opt` is defined and contains a value equal to `elem`, `false` otherwise
   * @example
   * {{{
   * Opt(5).contains(5)      // true
   * Opt(5).contains(10)     // false
   * Opt.empty.contains(5)   // false
   * }}}
   */
  infix inline final def contains[A1 >: A](elem: A1): Boolean = isDefined && this.get == elem

  /**
   * Tests if the predicate holds for the contained value.
   *
   * @param p the predicate to test
   * @return `true` if this `Opt` is defined and the predicate returns `true` for the contained value, `false` otherwise
   * @example
   * {{{
   * Opt(10).exists(_ > 5)   // true
   * Opt(3).exists(_ > 5)    // false
   * }}}
   */
  final def exists(p: A => Boolean): Boolean = isDefined && p(this.get)

  /**
   * Tests if the predicate holds for all elements (0 or 1).
   *
   * Returns `true` if this `Opt` is empty or the predicate holds for the contained value.
   *
   * @param p the predicate to test
   * @return `true` if this `Opt` is empty or the predicate returns `true` for the contained value
   * @example
   * {{{
   * Opt(10).forall(_ > 5)   // true
   * Opt(3).forall(_ > 5)    // false
   * Opt.empty.forall(_ > 5) // true
   * }}}
   */
  final def forall(p: A => Boolean): Boolean = isEmpty || p(this.get)

  /**
   * Applies a side-effecting function to the contained value if present.
   *
   * @param f the function to apply for its side effects
   * @tparam U the return type of the function (ignored)
   * @example
   * {{{
   * Opt(5).foreach(println)   // Prints: 5
   * Opt.empty.foreach(println) // No output
   * }}}
   */
  inline final def foreach[U](f: A => U): Unit = if (isDefined) f(this.get)

  /**
   * Returns the contained value or the provided default if empty.
   *
   * The default value is lazily evaluated.
   *
   * @param default the value to return if this `Opt` is empty
   * @tparam B a supertype of `A`
   * @return the contained value if defined, otherwise `default`
   * @example
   * {{{
   * Opt(5).getOrElse(0)      // 5
   * Opt.empty.getOrElse(0)   // 0
   * }}}
   */
  infix final def getOrElse[B >: A](default: => B): B = if (isEmpty) default else this.get

  /**
   * Returns this `Opt` if it contains a value, otherwise returns the provided alternative.
   *
   * The alternative is lazily evaluated.
   *
   * @param alternative the `Opt` to return if this `Opt` is empty
   * @tparam B a supertype of `A`
   * @return this `Opt` if defined, otherwise `alternative`
   * @example
   * {{{
   * Opt(5).orElse(Opt(10))      // Some(5)
   * Opt.empty.orElse(Opt(10))   // Some(10)
   * }}}
   */
  infix final def orElse[B >: A](alternative: => Opt[B]): Opt[B] = if (isEmpty) alternative else this

  /**
   * Returns the contained value or `null` if empty.
   *
   * This method only works if the type parameter can be `null`.
   *
   * @tparam A1 a supertype of `A` that can be `null`
   * @return the contained value if defined, otherwise `null`
   * @example
   * {{{
   * val result: String | Null = Opt("hello").orNull  // "hello"
   * val result: String | Null = Opt.empty.orNull     // null
   * }}}
   */
  final def orNull[A1 >: A](implicit ev: Null <:< A1): A1 = this getOrElse ev(null)

  /**
   * Transforms the contained value using the provided function.
   *
   * Returns `None` if this `Opt` is empty.
   *
   * @param f the function to apply to the contained value
   * @tparam B the type of the transformed value
   * @return an `Opt` containing the transformed value, or `None` if empty
   * @example
   * {{{
   * Opt(5).map(_ * 2)       // Some(10)
   * Opt.empty.map(_ * 2)    // None
   * Opt("hello").map(_.length) // Some(5)
   * }}}
   */
  infix final def map[B](f: A => B): Opt[B] = if (isEmpty) None else Some(f(this.get))

  /**
   * Applies a function that returns an `Opt` and flattens the result.
   *
   * This is the monadic bind operation.
   *
   * @param f the function to apply to the contained value, returning an `Opt`
   * @tparam B the type of the value in the returned `Opt`
   * @return the result of applying `f` to the contained value, or `None` if empty
   * @example
   * {{{
   * Opt(5).fold("empty")(_ + " items")      // "5 items"
   * Opt.empty.fold("empty")(_ + " items")   // "empty"
   * }}}
   */
  final def fold[B](ifEmpty: => B)(f: A => B): B = if (isEmpty) ifEmpty else f(this.get)

  /**
   * Applies a function that returns an `Opt` and flattens the result.
   *
   * Monadic bind operation. If the function returns a nested `Opt`, the result is automatically flattened.
   *
   * @param f the function to apply to the contained value, returning an `Opt`
   * @tparam B the type of the value in the returned `Opt`
   * @return the result of applying `f` to the contained value, or `None` if empty
   * @example
   * {{{
   * Opt(5).flatMap(x => Opt(x * 2))     // Some(10)
   * Opt.empty.flatMap(x => Opt(x * 2))  // None
   * Opt(5).flatMap(x => Opt.empty)      // None
   * }}}
   */
  final def flatMap[B](f: A => Opt[B]): Opt[B] = if (isEmpty) None else f(this.get)

  /**
   * Flattens a nested `Opt`.
   *
   * Extracts the inner `Opt` from `Opt[Opt[A]]` to get `Opt[A]`.
   *
   * @tparam B the type of the nested `Opt`
   * @return the flattened `Opt`, or `None` if this `Opt` or the nested `Opt` is empty
   * @example
   * {{{
   * Opt(Opt(5)).flatten    // Some(5)
   * Opt(Opt.empty).flatten // None
   * Opt.empty.flatten      // None
   * }}}
   */
  def flatten[B](implicit ev: A <:< Opt[B]): Opt[B] = if (isEmpty) None else ev(this.get)

  /**
   * Filters the contained value based on the provided predicate.
   *
   * Returns this `Opt` if the value satisfies the predicate, otherwise returns `None`.
   *
   * @param p the predicate to test
   * @return this `Opt` if the value satisfies the predicate or is empty, otherwise `None`
   * @example
   * {{{
   * Opt(10).filter(_ > 5)   // Some(10)
   * Opt(3).filter(_ > 5)    // None
   * Opt.empty.filter(_ > 5) // None
   * }}}
   */
  infix final def filter(p: A => Boolean): Opt[A] = if (isEmpty || p(this.get)) this else None

  /**
   * Filters the contained value based on the negation of the provided predicate.
   *
   * Returns this `Opt` if the predicate doesn't hold for the value, otherwise returns `None`.
   *
   * @param p the predicate to test
   * @return this `Opt` if the predicate doesn't hold for the value or is empty, otherwise `None`
   * @example
   * {{{
   * Opt(3).filterNot(_ > 5)   // Some(3)
   * Opt(10).filterNot(_ > 5)  // None
   * Opt.empty.filterNot(_ > 5) // None
   * }}}
   */
  infix final def filterNot(p: A => Boolean): Opt[A] = if (isEmpty || !p(this.get)) this else None

  /**
   * Combines this `Opt` with another `Opt` into an `Opt` of pairs.
   *
   * Returns `None` if either `Opt` is empty.
   *
   * @param that the other `Opt` to combine with
   * @tparam A1 a supertype of `A`
   * @tparam B  the type of the value in `that`
   * @return an `Opt` containing a pair of values if both are defined, otherwise `None`
   * @example
   * {{{
   * Opt(5).zip(Opt("hello"))        // Some((5, "hello"))
   * Opt(5).zip(Opt.empty)           // None
   * Opt.empty.zip(Opt("hello"))     // None
   * }}}
   */
  final def zip[A1 >: A, B](that: Opt[B]): Opt[(A1, B)] =
    if (isEmpty || that.isEmpty) None else Some((this.get, that.get))

  /**
   * Splits an `Opt` containing a pair into a pair of `Opt`s.
   *
   * @tparam A1 the first type of the pair
   * @tparam A2 the second type of the pair
   * @return a pair of `Opt`s containing the elements from the pair, or `(None, None)` if empty
   * @example
   * {{{
   * Opt((5, "hello")).unzip        // (Some(5), Some("hello"))
   * Opt.empty.unzip                // (None, None)
   * }}}
   */
  final def unzip[A1, A2](implicit asPair: A <:< (A1, A2)): (Opt[A1], Opt[A2]) = {
    if (isEmpty) {
      (None, None)
    } else {
      val e = asPair(this.get)
      (Some(e._1), Some(e._2))
    }
  }

  /**
   * Converts this `Opt` to a `List`.
   *
   * @return an empty `List` if this `Opt` is empty, a single-element `List` containing the value if defined
   * @example
   * {{{
   * Opt(5).toList        // List(5)
   * Opt.empty.toList     // List()
   * }}}
   */
  def toList: List[A] = if (isEmpty) List() else new::(this.get, Nil)
}

object Opt {
  /**
   * Creates an `Opt` containing the provided value.
   *
   * @param x the value to wrap
   * @tparam A the type of the value
   * @return an `Opt` containing the value
   * @example
   * {{{
   * val opt = Opt(42)  // Some(42)
   * }}}
   */
  def apply[A](x: A): Opt[A] = Some(x)

  /**
   * Creates an `Opt` containing the provided value.
   *
   * Alternative factory method to `apply`.
   *
   * @param x the value to wrap
   * @tparam A the type of the value
   * @return an `Opt` containing the value
   * @example
   * {{{
   * val opt = Opt.of("hello")  // Some("hello")
   * }}}
   */
  def of[A](x: A): Opt[A] = Some(x)

  /**
   * Returns an empty `Opt`.
   *
   * @tparam A the type parameter of the `Opt`
   * @return an empty `Opt`
   * @example
   * {{{
   * val empty = Opt.empty[String]  // None
   * }}}
   */
  def empty[A]: Opt[A] = None

  /**
   * Internal implementation of a non-empty `Opt` containing a single value.
   *
   * @tparam A the type of the contained value
   * @param value the contained value
   */
  private final case class Some[+A](value: A) extends Opt[A] {
    infix def get: A = value
  }

  /**
   * Internal implementation of an empty `Opt`.
   *
   * This is a singleton object used by all empty `Opt` instances.
   */
  private case object None extends Opt[Nothing] {
    inline def get: Nothing = throw new NoSuchElementException("None.get")
  }
}