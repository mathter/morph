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
 * A runtime exception that captures evaluation errors with detailed stack
 * trace context for debugging.
 *
 * Overview
 * --------
 * `EvalException` is thrown when an unrecoverable error occurs during the
 * evaluation of an [[Eval]] expression. Unlike silent failures (represented by
 * `Opt.none()`), an `EvalException` indicates a hard error that prevents normal
 * evaluation flow (e.g., type mismatch, system failure, or invariant violation).
 *
 * The exception includes a [[Tracer]] instance that captures the full call chain
 * at the point of error, enabling detailed debugging even in complex nested
 * evaluations.
 *
 * Core fields
 * -----------
 * - `tracer: Tracer` — Stack trace information captured during the error.
 *   Includes parent traces for nested evaluations.
 * - `message: String` — A human-readable error description (optional).
 * - `cause: Throwable` — The underlying exception that triggered this error
 *   (optional; may be null).
 *
 * Constructors
 * -----------
 * {{
 * // Full form: tracer + message + cause
 * new EvalException(tracer, message, cause)
 *
 * // Shortened form: tracer + cause (message is null)
 * new EvalException(tracer, cause)
 * }}
 *
 * When to throw
 * ---------------
 * Throw `EvalException` in these scenarios:
 *   - **Type errors**: An expression expected one type but received another
 *     (e.g., trying to add a string to a number).
 *   - **Invalid state**: An invariant violation or inconsistent context state.
 *   - **External failures**: I/O errors, network failures, or other system
 *     exceptions that occur during evaluation.
 *   - **Unrecoverable computation errors**: Division by zero (if not handled
 *     gracefully), stack overflow, etc.
 *
 * When NOT to throw
 * ------------------
 * Avoid throwing `EvalException` for:
 *   - **Missing data**: Return `Opt.none()` instead.
 *   - **Failed predicates**: If a condition fails (e.g., a guard in a filter),
 *     return `None` rather than throwing.
 *   - **Expected control flow**: Use `None` to signal normal, anticipated
 *     evaluation paths.
 *
 * Error messages
 * ---------------
 * The exception's `getMessage()` method returns a formatted string containing:
 *   1. The original message (if provided)
 *   2. A newline
 *   3. The full trace output from the [[Tracer]] (multi-line, indented)
 *
 * Example output
 * ---------------
 * {{
 * Type mismatch in field 'age': expected Int, got String
 * 	level: 15
 * 	stacktrace: io.github.mathter.morph.eval.Context.target(Context.scala:42)
 * 	prev:
 * 		level: 14
 * 		stacktrace: io.github.mathter.morph.dsl.IntField.eval(IntField.scala:28)
 * 		prev: ...
 * }}
 *
 * Trace capture
 * ---------------
 * The [[Tracer]] is typically captured at the point where the error originates,
 * using one of the static `trace*()` methods:
 *
 * {{
 * def eval(implicit context: Context): Opt[Int] = {
 *   val tracer = Tracer.trace4()  // Capture stack at depth 4
 *   try {
 *     someRiskyOperation()
 *   } catch {
 *     case ex: Throwable =>
 *       throw new EvalException(tracer, "Operation failed", ex)
 *   }
 * }
 * }}
 *
 * Debugging with EvalException
 * ---------------------------
 * When debugging:
 *   1. Print the exception: `println(ex.getMessage())` to see the full trace.
 *   2. Inspect the [[Tracer]]: `ex.tracer.toString()` for more details.
 *   3. Examine the cause: `ex.getCause()` for the underlying error (if any).
 *   4. Check the message: `ex.getMessage()` is the primary error description.
 *
 * Thread safety
 * ----------------
 * `EvalException` is thread-safe; the tracer uses thread-local storage to
 * maintain separate trace stacks per thread. However, if threads are reused
 * (e.g., in a thread pool), ensure proper cleanup or initialization of the
 * tracer to avoid trace pollution.
 *
 * Performance considerations
 * ---------------------------
 * - Constructing an `EvalException` captures the current stack trace, which
 *   has some cost. Avoid throwing/catching in tight loops.
 * - The trace output can be large for deeply nested evaluations. Log
 *   selectively to avoid excessive I/O.
 *
 * @param tracer The stack trace context captured at the point of error
 * @param message A description of the error; may be null
 * @param cause The underlying exception; may be null
 * @see [[Tracer]] for stack trace capture details
 */
class EvalException(val tracer: Tracer, message: String, throwable: Throwable)
  extends RuntimeException(message, throwable) {

  /**
   * Constructs an exception from a tracer and cause, with no explicit message.
   *
   * @param tracer The stack trace context
   * @param throwable The underlying exception
   */
  def this(tracer: Tracer, throwable: Throwable) = {
    this(tracer, null, throwable)
  }

  /**
   * Returns a message combining the user-provided description with the
   * full trace output.
   *
   * @return A formatted string containing the original message (if any) and
   *         the trace, separated by a newline. The trace is multi-line and
   *         indented for readability.
   */
  override def getMessage: String = {
    super.getMessage + '\n' + this.tracer.toString
  }
}
