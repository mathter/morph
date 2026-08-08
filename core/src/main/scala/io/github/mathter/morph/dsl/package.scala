package io.github.mathter.morph

/**
 * Domain-specific language (DSL) for constructing and evaluating path-based
 * expressions against `PathMap` values.
 *
 * Overview
 * --------
 * The `io.github.mathter.morph.dsl` package provides a compact, composable DSL
 * for selecting, transforming and validating data stored in hierarchical
 * `PathMap` structures. It is intentionally lightweight and functional in
 * style: DSL nodes are immutable, composable, and designed to be evaluated by
 * the runtime without hidden side-effects.
 *
 * Core abstractions
 * -----------------
 * - [[io.github.mathter.morph.dsl.Dsl]]
 *   The top-level expression type representing a computation that can be
 *   executed against a `PathMap` or other sources. Instances typically produce
 *   a `Result` when evaluated.
 *
 * - [[io.github.mathter.morph.dsl.Source]] and operator traits
 *   `Source` implementations (and the accompanying `*Ops` traits) represent
 *   typed producers of values: string, numeric, boolean, lists and path-map
 *   scoped values. Operator traits add convenient methods to build complex
 *   expressions using infix/functional forms.
 *
 * - [[io.github.mathter.morph.dsl.Composite]] / CompositeN
 *   Composite nodes combine multiple sub-expressions into a single value.
 *   Use these when mapping multiple fields into a structured result.
 *
 * - [[io.github.mathter.morph.dsl.Acceptor]] and `When` / `Then` / `Else`
 *   Constructs for conditional validation and branching within DSL trees.
 *   `Acceptor` implementations encapsulate predicate checks and associated
 *   acceptance or rejection behavior.
 *
 * - [[io.github.mathter.morph.dsl.PathMapDsl]] and [[io.github.mathter.morph.dsl.ListDsl]]
 *   Convenience helpers for working specifically with `PathMap` and list-like
 *   sources. They expose idiomatic methods to traverse, read and transform
 *   hierarchical data.
 *
 * Result and evaluation
 * ---------------------
 * The DSL yields [[io.github.mathter.morph.dsl.ResultDsl]] / `Result` values
 * that encode success, failure, and optional transformation metadata. Evaluation
 * is intentionally explicit: call sites provide the source (for example a
 * `PathMap`) and invoke the DSL to obtain a `Result` which can then be
 * inspected or converted into application types.
 *
 * Example
 * -------
 * {{
 * import io.github.mathter.morph.dsl.*
 *
 * val expr = Dsl.field("title")  // a Source or Dsl that picks `title`
 * val result = expr.evaluate(pathMap)
 * result match {
 *   case Result.Success(v) => println(v)
 *   case Result.Failure(err) => println(s"error: $err")
 * }
 * }}
 *
 * Implementation notes
 * --------------------
 * - Implementations in this package are optimized for composition and clarity
 *   rather than extreme performance; avoid leaking mutable state through DSL
 *   nodes.  
 * - The `*Ops` traits provide fluent helpers and implicit conversions to make
 *   the DSL ergonomic in Scala code. Java callers should use the explicit
 *   factory methods on `PathMapDsl` / `Dsl` where possible.
 *
 * See also: [[io.github.mathter.morph.path.Path]] and [[io.github.mathter.morph.data.PathMap]]
 * for details about the underlying data model the DSL operates on.
 */
package object dsl
