package io.github.mathter.morph.eval

import io.github.mathter.morph.data.{Opt, PathMap}

/**
 * The evaluation environment that carries state and provides access to both
 * source data and intermediate/result values during computation.
 *
 * Overview
 * --------
 * A `Context` instance is passed (implicitly) through the entire evaluation of
 * a DSL expression tree. It serves two critical roles:
 *   1. **Source access**: Provides the input `PathMap` (`origin`) containing
 *      the data being transformed or queried.
 *   2. **State storage**: Provides a typed, tag-scoped store (`target`) for
 *      accumulating intermediate and final results, enabling memoization,
 *      variable binding, and inter-evaluation communication.
 *
 * The `origin` data is typically immutable; the `target` store may accumulate
 * values as evaluation proceeds, but a well-designed implementation will not
 * require mutation to achieve correct results.
 *
 * Core methods
 * -----------
 * - `origin: PathMap` — The input data, typically a structured map of values
 *   to be transformed.
 * - `target[T](tag: Any): Opt[T]` — Reads a value from the result store,
 *   scoped by `tag`.
 * - `target[T](tag: Any, opt: Opt[T]): Opt[T]` — Writes/stores a value
 *   (and reads it back), allowing accumulation of results.
 *
 * Scoping and isolation
 * ---------------------
 * The `tag: Any` parameter in the `target` methods acts as a key for isolating
 * stored values. Common patterns include:
 *   - **String tags**: `context.target("result")` for named results
 *   - **Object identity**: `context.target(someExpr)` to store per-expression
 *   - **Type tags**: `context.target(typeOf[MyClass])` for type-driven scoping
 *   - **Field names**: `context.target("user.name")` for path-scoped values
 *
 * Implementing contexts
 * ---------------------
 * Implementations should:
 *   - Ensure `origin` is stable and immutable.
 *   - Provide efficient, thread-safe read access to both `origin` and stored
 *     values (write access need not be concurrent).
 *   - Support nesting: derived contexts may shadow or extend parent scopes,
 *     allowing local variable bindings or nested evaluations to function
 *     correctly.
 *   - Guarantee that the same `tag` always refers to the same stored value
 *     within a single evaluation; mutation of stored values must not occur
 *     mid-evaluation.
 *
 * Usage examples
 * ---------------
 * {{
 * // Reading from origin
 * val userMap: PathMap = context.origin
 * val name: Option[String] = userMap.get("name")
 *
 * // Storing and retrieving computed values
 * val computed: Opt[Int] = context.target("count", Opt.some(42))
 * val retrieved: Opt[Int] = context.target("count")
 *
 * // Using object identity as a tag (e.g., for expression-local results)
 * val exprA: Eval[String] = ???
 * context.target(exprA, Opt.some("result_A"))
 * }}
 *
 * Lifecycle
 * ----------
 * - A `Context` is typically created at the start of a top-level evaluation,
 *   with `origin` set to the input data.
 * - As evaluation proceeds, intermediate results accumulate in `target`.
 * - When evaluation completes (successfully, via `None`, or via exception),
 *   the context is no longer needed; it may be discarded.
 * - Nested evaluations (e.g., within sub-expressions) may use derived contexts
 *   with modified or extended scopes.
 *
 * Error handling
 * ---------------
 * The `Context` interface itself does not throw exceptions; failed lookups
 * simply return `Opt.none()`. However, implementations may throw
 * `EvalException` if internal invariants are violated.
 *
 * @see [[Eval]] for the computation trait that uses this context
 * @see [[io.github.mathter.morph.data.PathMap]] for the structure of `origin`
 * @see [[io.github.mathter.morph.data.Opt]] for optional value semantics
 */
trait Context {
  /**
   * The input data being evaluated or transformed.
   *
   * Typically a `PathMap` containing hierarchical key-value pairs representing
   * the source object or document. This field is read-only and should be
   * immutable.
   *
   * @return The source `PathMap`
   */
  def origin: PathMap

  /**
   * Retrieves a previously stored value from the result/variable store.
   *
   * @tparam T The expected type of the stored value
   * @param tag A unique key identifying the stored value. Can be any object;
   *            equality (`==`) is used to match tags.
   * @return `Opt.some(value)` if a value with the given tag exists in the store,
   *         `Opt.none()` otherwise.
   */
  def target[T](tag: Any): Opt[T]

  /**
   * Stores a value in the result/variable store and returns it.
   *
   * This method is used to accumulate intermediate results, bind variables,
   * or memoize computed values. The typical usage pattern is:
   *
   * {{
   * val result: Opt[T] = context.target(tag, someComputation())
   * // result is now stored and subsequent calls to context.target(tag)
   * // will return it
   * }}
   *
   * @tparam T The type of value being stored
   * @param tag A unique key identifying the value in the store. Can be any
   *            object; equality (`==`) is used as the key.
   * @param opt The value to store (wrapped in `Opt`). If `Opt.none()`, the
   *            store may or may not retain a binding; implementations may vary.
   * @return The same `opt` value that was passed in; this allows the method
   *         to be used in a functional chain.
   */
  def target[T](tag: Any, opt: Opt[T]): Opt[T]
}
