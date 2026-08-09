package io.github.mathter.morph.eval

import io.github.mathter.morph.data.Opt

/**
 * A lazy computation that produces an optional value of type `T` when evaluated
 * within a given context.
 *
 * Overview
 * --------
 * `Eval[T]` is the fundamental abstraction in the Morph evaluation framework.
 * It represents a deferred computation that can be evaluated against source
 * data (via a `Context`) to produce an optional result. All expressions in the
 * DSL tree ultimately conform to this interface, enabling uniform composition
 * and execution.
 *
 * Design principles
 * -----------------
 * - **Lazy evaluation**: The computation is not executed until `eval()` is
 *   called; multiple invocations on the same instance produce independent
 *   evaluations.
 * - **Immutability**: `Eval` instances are immutable and thread-safe for reading.
 *   Results are accumulated in the provided `Context`, not in the `Eval` itself.
 * - **Optional semantics**: The result type `Opt[T]` represents "possibly no
 *   result", allowing expressions to gracefully indicate missing, invalid, or
 *   undefined values without throwing exceptions.
 * - **Implicit context**: The `context` parameter is implicit, allowing it to be
 *   passed through call chains without explicit threading while remaining
 *   traceable in the code.
 *
 * Type parameter
 * ---------------
 * @tparam T The type of value produced by this evaluation. May be a primitive
 *          (e.g., `Int`, `String`), a collection, a structured type (e.g.,
 *          `PathMap`), or any application-specific class.
 *
 * Usage
 * -----
 * Typically, you will not implement `Eval` directly; instead, use DSL builders
 * or framework utilities that return `Eval` instances. However, custom
 * implementations are straightforward:
 *
 * {{
 * // Simple literal evaluation
 * case class Literal[T](value: T) extends Eval[T] {
 *   def eval(implicit context: Context): Opt[T] = Opt.some(value)
 * }
 *
 * // Evaluation that depends on context state
 * case class ReadTarget[T](tag: Any) extends Eval[T] {
 *   def eval(implicit context: Context): Opt[T] = context.target(tag)
 * }
 *
 * // Evaluation that may fail
 * case class SafeDiv(num: Eval[Double], denom: Eval[Double]) extends Eval[Double] {
 *   def eval(implicit context: Context): Opt[Double] =
 *     for {
 *       n <- num.eval
 *       d <- denom.eval
 *       if d != 0.0
 *     } yield n / d
 * }
 * }}
 *
 * Composition
 * -----------
 * `Eval` instances can be composed using monadic (flatMap/for) and applicative
 * patterns. For example:
 *
 * {{
 * val expr1: Eval[Int] = ???
 * val expr2: Eval[Int] = ???
 *
 * val combined: Eval[Int] = for {
 *   a <- expr1
 *   b <- expr2
 * } yield a + b
 * }}
 *
 * Error handling
 * ---------------
 * - Return `Opt.none()` or `Option.empty` for missing or invalid data.
 * - Throw an `EvalException` for unrecoverable errors (e.g., type mismatches,
 *   system failures). The exception will include full trace context.
 * - Avoid silent failures in custom implementations; prefer explicit `None`
 *   values for known error cases.
 *
 * Thread safety
 * ---------------
 * `Eval` implementations should be stateless or use only immutable state.
 * The same `Eval[T]` instance can be safely evaluated concurrently against
 * different `Context` instances (or the same context if it provides thread-safe
 * read access).
 *
 * Performance considerations
 * ---------------------------
 * - Evaluation is single-pass; if you need a value more than once, store it
 *   in `Context.target()`.
 * - Avoid deep nesting of `Eval` chains in tight loops; consider materializing
 *   intermediate results.
 * - Custom implementations should complete quickly; long-running I/O or
 *   computations should be wrapped explicitly to avoid blocking the caller.
 *
 * @see [[Context]] for evaluation environment
 * @see [[EvalException]] for error handling
 * @see [[Tracer]] for debugging support
 */
trait Eval[T] {
  /**
   * Evaluates this computation within the given context.
   *
   * @param context The evaluation environment, providing access to source
   *                 data (origin) and intermediate results (target store).
   *                 Implicitly passed; ensure it is available in scope.
   * @return An `Opt[T]` representing the result. `Opt.some(value)` on success,
   *         `Opt.none()` if the computation cannot produce a value (e.g., a
   *         field is missing or a condition fails).
   * @throws EvalException on unrecoverable errors (e.g., type errors, external
   *                       I/O failures). The exception includes detailed trace
   *                       information.
   */
  def eval(implicit context: Context): Opt[T]
}
