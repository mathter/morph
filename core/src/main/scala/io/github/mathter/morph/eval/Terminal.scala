// Copyright (c) 2026 mathter
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, without
// warranties or condition of any kind. You may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// All rights reserved.
package io.github.mathter.morph.eval

/**
 * A marker trait for leaf values in the DSL tree.
 *
 * Overview
 * --------
 * `Terminal` serves as a boundary marker in expression hierarchies. A terminal
 * represents a concrete, indivisible value or primitive operation that cannot
 * be decomposed into smaller sub-expressions. Terminals are typically the
 * leaves of an abstract syntax tree (AST) and form the basis upon which
 * complex, composite expressions are built.
 *
 * Purpose
 * -------
 * - **Type distinction**: Allows the framework to distinguish between
 *   composite expressions (which may be built from other expressions) and
 *   terminal values (which represent base cases).
 * - **Translation**: Often used in conjunction with [[Translator]] to convert
 *   terminal instances into evaluable [[Eval]] expressions.
 * - **Boundary definition**: Provides a clear demarcation between the "what to
 *   compute" (expressions and terminals) and the "how to compute" (evaluators
 *   and translators).
 *
 * Implementing Terminal
 * ---------------------
 * Custom implementations typically represent:
 *   - **Literal values**: A constant number, string, or other immutable value
 *   - **Variable references**: A reference to a named variable or field
 *   - **Built-in operations**: A primitive operation or function call
 *   - **External data access**: A reference to a field in the input data
 *
 * Example
 * -------
 * {{
 * // A terminal representing a literal string
 * case class StringLiteral(value: String) extends Terminal
 *
 * // A terminal representing a field access
 * case class FieldAccess(path: String) extends Terminal
 *
 * // A terminal representing a built-in function
 * case class FunctionCall(name: String, args: Seq[Terminal]) extends Terminal
 * }}
 *
 * Relationship to Eval and Translator
 * -----------------------------------
 * A `Terminal` by itself is inert; it contains no evaluation logic. To make a
 * terminal computable, it must be converted to an [[Eval]] via a
 * [[Translator]]:
 *
 * {{
 * val terminal: Terminal = StringLiteral("hello")
 * val eval: Eval[String] = translator.translate(terminal)
 * val context: Context = ???
 * val result: Opt[String] = eval.eval(context)
 * }}
 *
 * This separation enables:
 *   - Multiple translation strategies for the same terminal (e.g., strict vs.
 *     lenient evaluation)
 *   - AST manipulation before evaluation (e.g., optimization, validation)
 *   - Language-agnostic terminal representations
 *
 * Design rationale
 * ----------------
 * As a marker trait with no abstract methods, `Terminal` is intentionally
 * minimal. This design:
 *   - Allows terminal implementations to be simple data classes without
 *     boilerplate.
 *   - Enables pattern matching on terminals without knowing their specific type
 *     (via `Terminal` type).
 *   - Keeps the framework open to extension; new terminal types can be added
 *     without modifying the trait itself.
 *
 * @see [[Translator]] for converting terminals to evaluable expressions
 * @see [[Eval]] for the evaluation interface
 */
trait Terminal {

}
