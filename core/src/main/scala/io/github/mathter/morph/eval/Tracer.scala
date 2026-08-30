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

import scala.collection.mutable

/**
 * Captures and manages nested stack traces during evaluation for debugging
 * and error reporting.
 *
 * Overview
 * --------
 * `Tracer` is a utility class that tracks the call chain during evaluation of
 * an [[Eval]] expression tree. It maintains a thread-local stack of trace
 * frames, allowing detailed error messages to be constructed that show exactly
 * where in the evaluation chain an error occurred, even in complex nested
 * computations.
 *
 * Design
 * ------
 * - **Hierarchical**: Each `Tracer` instance holds a reference to its parent
 *   (or null if it's the root), forming a linked list that represents the
 *   call chain.
 * - **Stack-based**: A thread-local stack is maintained per thread to manage
 *   the active trace frames during nested evaluations.
 * - **Lazy capture**: Stack trace information is captured only when a tracer
 *   is constructed, avoiding overhead for successful evaluations.
 * - **Immutable**: Once constructed, a `Tracer` instance is immutable; new
 *   frames are pushed onto the thread-local stack, not onto the tracer itself.
 *
 * Core fields
 * -----------
 * - `parent: Tracer` — The parent tracer, forming a linked chain. Null for
 *   root frames.
 * - `stackTraceElement: StackTraceElement` — Information about this frame:
 *   class name, method name, file, and line number.
 * - `level: Int` — The stack depth (length of the call stack when this frame
 *   was captured). Used for cleanup and validation.
 *
 * Static factory methods
 * ----------------------
 * Tracer provides four factory methods for capturing traces at different call
 * depths:
 *   - `trace3()` — Captures at depth 3 (for direct frame calls)
 *   - `trace4()` — Captures at depth 4 (typical for most evaluators)
 *   - `trace5()` — Captures at depth 5 (for wrapper methods)
 *   - `trace6()` — Captures at depth 6 (for deeply nested wrappers)
 *
 * Each method:
 *   1. Obtains the current thread's stack trace.
 *   2. Pops expired frames from the thread-local stack (those whose depth
 *      exceeds the current stack size).
 *   3. Creates a new `Tracer` with the appropriate stack frame and depth.
 *   4. Pushes it onto the stack.
 *   5. Returns the tracer for use in error reporting.
 *
 * Usage examples
 * ---------------
 * {{
 * // In an Eval implementation
 * def eval(implicit context: Context): Opt[Int] = {
 *   val tracer = Tracer.trace4()
 *   try {
 *     someComputation()
 *   } catch {
 *     case ex: Throwable =>
 *       throw new EvalException(tracer, "Computation failed", ex)
 *   }
 * }
 *
 * // In a nested evaluation
 * def complexEval(implicit context: Context): Opt[String] = {
 *   val tracer = Tracer.trace5()  // One level deeper than direct eval
 *   try {
 *     nestedComputation()
 *   } catch {
 *     case ex: EvalException =>
 *       throw ex  // Already has trace info
 *     case ex: Throwable =>
 *       throw new EvalException(tracer, ex)
 *   }
 * }
 * }}
 *
 * Output format
 * ---------------
 * The `toString` method produces a multi-line, indented trace output:
 *
 * {{
 *   level: 25
 *   stacktrace: io.github.mathter.morph.eval.MyEval.eval(MyEval.scala:42)
 *   prev:
 *     level: 24
 *     stacktrace: io.github.mathter.morph.eval.ParentEval.eval(ParentEval.scala:35)
 *     prev: ...
 * }}
 *
 * This format is designed to be human-readable and suitable for logging.
 *
 * Thread safety
 * ---------------
 * `Tracer` uses a `ThreadLocal[mutable.Stack[Tracer]]` to isolate traces per
 * thread. This means:
 *   - Each thread has its own independent stack of active traces.
 *   - Multiple threads can evaluate expressions concurrently without
 *     interference.
 *   - However, in thread-pool scenarios, ensure proper cleanup or reuse of
 *     thread-local state to avoid trace pollution or memory leaks.
 *
 * Stack management
 * ----------------
 * The thread-local stack is automatically managed by the factory methods:
 *   - Frames are pushed when created.
 *   - Expired frames (whose stack depth exceeds the current stack size) are
 *     automatically popped to prevent memory leaks and ensure accurate
 *     parenting.
 *   - A well-behaved application will naturally clean up the stack as the call
 *     stack unwinds (although explicit cleanup may be needed in thread pools).
 *
 * Choosing the right depth
 * -------------------------
 * The depth parameter (3, 4, 5, 6) should match the position of the call stack:
 *   - Position 0: Thread.currentThread().getStackTrace() (in the JVM)
 *   - Position 1: getStackTrace() method
 *   - Position 2: trace3/trace4/etc. factory method
 *   - Position 3: Your code calling trace3()
 *   - Position 4: Your code's caller (if wrapped)
 *
 * Example:
 * {{
 *   public void methodA() {
 *     Tracer.trace4();  // trace4 because trace3 would be methodA
 *   }
 *
 *   private void methodB() {
 *     methodA();  // Stack: ... -> methodB -> methodA -> trace4()
 *   }
 * }}
 *
 * Performance considerations
 * ---------------------------
 * - Capturing a stack trace has some cost; avoid creating tracers in tight
 *   loops unless error handling justifies it.
 * - The trace is only used when an error occurs, so the overhead is typically
 *   amortized over successful evaluations.
 * - The thread-local stack is minimal in size and should not cause memory
 *   issues under normal usage.
 *
 * Common pitfalls
 * ----------------
 * - **Wrong depth**: Using `trace3()` when you should use `trace4()` will
 *   capture the wrong frame. Measure the stack depth at the call site to
 *   determine the correct method.
 * - **Thread reuse**: In servlet or thread-pool environments, the stack may
 *   not be cleaned up between requests. Consider resetting the thread-local
 *   stack at suitable boundaries.
 * - **Memory in errors**: If your application logs a large number of
 *   `EvalException`s (which include full traces), be prepared for the memory
 *   and I/O overhead.
 *
 * @param parent The parent tracer in the call chain, or null if this is a
 *               root frame.
 * @param stackTraceElement The JVM stack trace element (class, method, file,
 *                          line) at this frame.
 * @param level The stack depth (length of the call stack) when this tracer
 *              was created.
 * @see [[EvalException]] for usage in exception construction
 */
class Tracer(private val parent: Tracer,
             private val stackTraceElement: StackTraceElement,
             private val level: Int
            ) {
  /**
   * Returns a formatted, multi-line string representation of this tracer and
   * its parent chain.
   *
   * @return A string containing the level, stacktrace element, and parent
   *         tracer (if any), each on separate lines and indented for clarity.
   */
  override def toString: String = {
    val sb = new StringBuilder()
    sb.append("\tlevel: ").append(this.level).append('\n')
    sb.append("\tstacktrace: ").append(this.stackTraceElement).append('\n')

    if (this.parent != null) {
      sb.append("\tprev: ").append('\n').append(this.parent).append('\n')
    }

    sb.toString()
  }
}

/**
 * Factory for creating and managing `Tracer` instances.
 *
 * Overview
 * --------
 * The `Tracer` companion object provides static factory methods for creating
 * tracers at different call depths and manages the thread-local stack that
 * coordinates nested evaluation traces.
 *
 * Thread-local stack
 * ------------------
 * A single `ThreadLocal[mutable.Stack[Tracer]]` is shared by all tracers and
 * all threads:
 *   - Initialized to an empty stack when the JVM starts.
 *   - Each thread gets its own independent stack instance.
 *   - The stack is used to track active trace frames and to clean up expired
 *     frames during nested evaluations.
 */
object Tracer {
  /**
   * Thread-local stack of active tracers, one per thread.
   *
   * Initialized once at class load time to an empty stack.
   */
  private val local = new ThreadLocal[mutable.Stack[Tracer]]

  local.set(mutable.Stack())

  /**
   * Captures a trace at stack depth 3.
   *
   * Use this method when calling directly from code that needs trace info.
   * The captured frame will be the caller of this method.
   *
   * @return A new `Tracer` instance representing the current frame and call
   *         chain. The tracer is pushed onto the thread-local stack.
   */
  def trace3(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(3), stackTrace.length)
    stack.push(tracer)
    tracer
  }

  /**
   * Captures a trace at stack depth 4.
   *
   * This is the most commonly used method, suitable for traces captured in
   * `Eval` implementations or methods called by evaluators.
   *
   * @return A new `Tracer` instance representing the current frame and call
   *         chain. The tracer is pushed onto the thread-local stack.
   */
  def trace4(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(4), stackTrace.length)
    stack.push(tracer)
    tracer
  }

  /**
   * Captures a trace at stack depth 5.
   *
   * Use this method when one or more wrapper methods separate your code from
   * the point where trace info is needed.
   *
   * @return A new `Tracer` instance representing the current frame and call
   *         chain. The tracer is pushed onto the thread-local stack.
   */
  def trace5(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(5), stackTrace.length)
    stack.push(tracer)
    tracer
  }

  /**
   * Captures a trace at stack depth 6.
   *
   * Use this method when multiple wrapper or utility methods separate your
   * code from the point where trace info is needed.
   *
   * @return A new `Tracer` instance representing the current frame and call
   *         chain. The tracer is pushed onto the thread-local stack.
   */
  def trace6(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(6), stackTrace.length)
    stack.push(tracer)
    tracer
  }
}
