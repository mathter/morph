package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;

/**
 * String-valued source with common string operations exposed as
 * transformation-friendly methods.
 * <p>
 * Supports casing, trimming, replacements, length queries and blank/empty
 * checks, returning appropriate {@link StringSource}, {@link NumberSource}
 * or {@link BooleanSource} wrappers so these operations can be composed
 * inside transformation DSL expressions.
 *
 * @options none
 * @packages io.github.mathter.jmorph.dsl
 */
public interface StringSource extends Source<String> {
    /**
     * Converts the string value to upper case.
     *
     * @return a {@link StringSource} producing the upper-cased string
     */
    public StringSource toUpperCase();

    /**
     * Converts the string value to lower case.
     *
     * @return a {@link StringSource} producing the lower-cased string
     */
    public StringSource toLowerCase();

    /**
     * Replaces all occurrences matching the given regular expression with
     * the replacement string.
     *
     * @param regexpr     regular expression to match
     * @param replacement replacement text
     * @return a {@link StringSource} with replacements applied
     */
    public StringSource replaceAll(String regexpr, String replacement);

    /**
     * Returns the length of the string as a {@link NumberSource} of
     * {@link Integer}.
     *
     * @return a {@link NumberSource} producing the string length
     */
    public NumberSource<Integer> length();

    /**
     * Trims whitespace from both ends of the string.
     *
     * @return a {@link StringSource} producing the trimmed string
     */
    public StringSource trim();

    /**
     * Checks whether the string is empty (length == 0).
     *
     * @return a {@link BooleanSource} that is true when the string is empty
     */
    public BooleanSource isEmpty();

    /**
     * Negation of {@link #isEmpty()}.
     *
     * @return a {@link BooleanSource} that is true when the string is not
     * empty
     */
    public BooleanSource notEmpty();

    /**
     * Checks whether the string is blank (empty or only whitespace).
     *
     * @return a {@link BooleanSource} that is true when the string is blank
     */
    public BooleanSource isBlank();

    /**
     * Negation of {@link #isBlank()}.
     *
     * @return a {@link BooleanSource} that is true when the string is not
     * blank
     */
    public BooleanSource notBlank();
}