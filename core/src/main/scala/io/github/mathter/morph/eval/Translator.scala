package io.github.mathter.morph.eval

/**
 * Converts [[Terminal]] instances into evaluable [[Eval]] expressions.
 *
 * Overview
 * --------
 * A `Translator` acts as a bridge between two representations:
 *   - **Input**: A [[Terminal]], a declarative description of a value or
 *     operation to compute.
 *   - **Output**: An [[Eval[T]]], an executable computation that produces an
 *     optional value when called.
 *
 * This separation enables clean separation of concerns:
 *   - Terminals can be built, manipulated, and analyzed statically (e.g., for
 *     validation or optimization) without invoking evaluation logic.
 *   - Multiple translators can coexist, each implementing different evaluation
 *     strategies or handling different terminal types.
 *   - The framework can support multiple DSL or language frontends that all
 *     converge on the same terminal representation.
 *
 * Core method
 * -----------
 * {{
 * def translate[T](terminal: Terminal): Eval[T]
 * }}
 *
 * Translates a `Terminal` into an `Eval[T]` that can be executed. The type
 * parameter `T` is typically inferred from the terminal's structure or the
 * call context.
 *
 * Implementation patterns
 * ----------------------
 * Typical translators:
 *   1. **Pattern-match on terminal type**: Dispatch to specialized translation
 *      logic based on the terminal's concrete class.
 *   2. **Recursively translate sub-terminals**: If a terminal contains nested
 *      terminals, translate them to nested `Eval` expressions.
 *   3. **Wrap in evaluation logic**: Enclose the terminal's semantics in an
 *      `Eval` instance that performs the actual computation.
 *
 * Example
 * -------
 * {{
 * object MyTranslator extends Translator {
 *   def translate[T](terminal: Terminal): Eval[T] = terminal match {
 *     case StringLiteral(value) =>
 *       new Eval[String] {
 *         def eval(implicit context: Context) =
 *           Opt.some(value)
 *       }.asInstanceOf[Eval[T]]
 *
 *     case FieldAccess(path) =>
 *       new Eval[Any] {
 *         def eval(implicit context: Context) =
 *           context.origin.get(path)
 *       }.asInstanceOf[Eval[T]]
 *
 *     case _ => throw new UnsupportedOperationException(...)
 *   }
 * }
 * }}
 *
 * Composition and chaining
 * ------------------------
 * Translators can be composed or chained:
 *   - **Decorator pattern**: Wrap one translator with another to add caching,
 *     logging, or transformation logic.
 *   - **Fallback chain**: Try multiple translators in sequence, using the first
 *     one that succeeds.
 *   - **Strategy selection**: Select a translator based on context (e.g.,
 *     strict vs. lenient evaluation).
 *
 * Example of composition
 * ----------------------
 * {{
 * // A caching translator that wraps another
 * class CachingTranslator(wrapped: Translator) extends Translator {
 *   private val cache = mutable.Map[Terminal, Eval[_]]()
 *
 *   def translate[T](terminal: Terminal): Eval[T] = {
 *     cache.getOrElseUpdate(terminal, wrapped.translate(terminal))
 *       .asInstanceOf[Eval[T]]
 *   }
 * }
 * }}
 *
 * Type erasure considerations
 * ---------------------------
 * Note that `Eval[T]` is subject to type erasure at runtime. When
 * implementing a translator, be aware that:
 *   - The `T` type parameter is not directly available at runtime.
 *   - Terminal types should encode sufficient information to determine the
 *     intended result type (e.g., via a field or by constructor convention).
 *   - Type casting (`.asInstanceOf`) may be necessary to satisfy the Scala
 *     type system.
 *
 * Error handling
 * ---------------
 * A translator should:
 *   - Return a valid `Eval[T]` for every supported terminal type.
 *   - Either throw an exception immediately (during translation) or return an
 *     `Eval` that throws or produces `None` during evaluation for unsupported
 *     terminals.
 *   - Preserve error context (e.g., stack traces or source locations) when
 *     translating error-producing terminals.
 *
 * @see [[Terminal]] for the input type
 * @see [[Eval]] for the output type
 */
trait Translator {
  /**
   * Translates a terminal value into an evaluable expression.
   *
   * @tparam T The expected type of the terminal's result. Should be inferred
   *           from the terminal's structure or the call context.
   * @param terminal The terminal to translate. This is a declarative
   *                description of the value or operation to compute.
   * @return An `Eval[T]` that, when evaluated in a context, will produce a
   *         value of type `T` (or `None` if evaluation fails).
   * @throws UnsupportedOperationException or similar if the translator cannot
   *         handle the given terminal type.
   * @throws ClassCastException if the terminal's actual result type does not
   *         match the expected type `T`.
   */
  def translate[T](terminal: Terminal): Eval[T]
}
