package io.github.mathter.morph

/**
 * Evaluation framework for executing path-based transformations and queries
 * against hierarchical data structures.
 *
 * Overview
 * --------
 * The `io.github.mathter.morph.eval` package provides the core evaluation
 * machinery for the Morph transformation engine. It defines the interfaces and
 * utilities needed to execute computations (represented as DSL nodes or other
 * expressions) against source data and produce typed results.
 *
 * Core abstractions
 * -----------------
 * - [[io.github.mathter.morph.eval.Eval]]
 *   A type-parameterized trait representing a lazy computation that produces
 *   an optional value of type `T` when evaluated. All evaluable expressions
 *   conform to this interface, enabling composition and deferred execution.
 *
 * - [[io.github.mathter.morph.eval.Context]]
 *   The evaluation environment that carries immutable state during computation.
 *   Provides access to:
 *     - `origin`: The source `PathMap` containing input data
 *     - `target`: A typed storage for intermediate and final results, scoped
 *       by tag-based keys
 *   Implementations manage variable scoping and provide isolation between
 *   nested evaluations.
 *
 * - [[io.github.mathter.morph.eval.Terminal]]
 *   A marker trait for leaf values in the DSL tree. Terminals represent
 *   concrete values or operations that cannot be decomposed further, serving
 *   as boundary points in expression trees.
 *
 * - [[io.github.mathter.morph.eval.Translator]]
 *   A converter interface that transforms `Terminal` instances into evaluable
 *   `Eval` expressions. Used to bridge between different representational
 *   layers and custom expression types.
 *
 * Error handling and debugging
 * ---------------------------
 * - [[io.github.mathter.morph.eval.EvalException]]
 *   A runtime exception that captures evaluation errors and includes detailed
 *   stack trace information via a [[io.github.mathter.morph.eval.Tracer]].
 *   Provides human-readable error messages that preserve the full evaluation
 *   call chain for debugging.
 *
 * - [[io.github.mathter.morph.eval.Tracer]]
 *   A utility class for capturing and managing nested stack traces during
 *   evaluation. Maintains a thread-local stack of trace frames, allowing
 *   accurate error reporting even in recursive or highly nested computations.
 *   Methods like `trace3()`, `trace4()`, etc., capture stack information at
 *   different call depths.
 *
 * Evaluation lifecycle
 * --------------------
 * 1. A DSL expression (or custom `Eval` implementation) is constructed
 *    representing a computation over input data.
 * 2. An evaluation `Context` is created, typically containing the input
 *    `PathMap` as the origin data and an empty target store.
 * 3. The `Eval[T].eval(context)` method is called, returning an `Opt[T]`
 *    (optional value). The computation may:
 *     - Succeed, producing a value `Some(result)`
 *     - Fail silently, producing `None`
 *     - Throw an `EvalException` on hard errors
 * 4. Results are collected and optionally aggregated by higher-level
 *    orchestration logic.
 *
 * Composition and reusability
 * ---------------------------
 * - `Eval` instances are immutable and composable; they can be safely shared
 *   and re-evaluated against different contexts.
 * - `Context` implementations should be thread-safe for read access but need
 *   not support concurrent mutation; a new context is typically created for
 *   each top-level evaluation.
 * - Nested evaluations can use context variants that shadow or extend parent
 *   scopes, enabling clean variable isolation.
 *
 * Example usage
 * -----------
 * {{
 * import io.github.mathter.morph.eval.*
 * import io.github.mathter.morph.data.Opt
 *
 * // Define a custom Eval implementation
 * case class LiteralEval(value: Int) extends Eval[Int] {
 *   def eval(implicit context: Context): Opt[Int] = Opt.some(value)
 * }
 *
 * // Create an evaluation context
 * val context: Context = ???  // typically provided by framework
 *
 * // Evaluate an expression
 * val expr: Eval[Int] = LiteralEval(42)
 * val result: Opt[Int] = expr.eval(context)
 * }}
 *
 * Thread safety and performance
 * ----------------------------
 * - `Tracer` uses `ThreadLocal` to isolate stack frames per thread; ensure
 *   proper cleanup in thread pool environments.
 * - Evaluations are lazy and single-pass; avoid repeated evaluation of the
 *   same expression in tight loops—cache results in the `Context.target` store
 *   instead.
 * - `EvalException` messages include full trace output; keep this in mind when
 *   logging large numbers of errors.
 *
 * See also: [[io.github.mathter.morph.data.PathMap]] for the underlying data
 * structure, and [[io.github.mathter.morph.dsl]] for DSL-based expression
 * builders.
 */
package object eval
