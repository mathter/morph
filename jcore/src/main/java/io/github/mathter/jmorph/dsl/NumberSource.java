package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;

/**
 * Numeric source expression supporting common arithmetic operations.
 * <p>
 * Provides arithmetic combinators (plus, minus, multiply, divide, rem),
 * unary operations (abs, negate) and sign extraction. Intended to wrap
 * numeric {@link io.github.mathter.morph.dsl.Source} instances with
 * fluent arithmetic capabilities used in transformation definitions.
 *
 * @param <T> the concrete numeric type
 * @options none
 * @packages io.github.mathter.jmorph.dsl
 */
public interface NumberSource<T extends Number> extends Source<T> {
    /**
     * Addition of two numeric sources.
     *
     * @param source right-hand operand
     * @return a {@link NumberSource} producing the sum
     */
    public NumberSource<T> plus(NumberSource<T> source);

    /**
     * Subtraction of two numeric sources (this - source).
     *
     * @param source right-hand operand
     * @return a {@link NumberSource} producing the difference
     */
    public NumberSource<T> minus(NumberSource<T> source);

    /**
     * Multiplication of two numeric sources.
     *
     * @param source multiplier
     * @return a {@link NumberSource} producing the product
     */
    public NumberSource<T> multiply(NumberSource<T> source);

    /**
     * Division of two numeric sources (this / source).
     *
     * @param source divisor
     * @return a {@link NumberSource} producing the quotient
     */
    public NumberSource<T> divide(NumberSource<T> source);

    /**
     * Remainder of division between two numeric sources.
     *
     * @param source divisor
     * @return a {@link NumberSource} producing the remainder
     */
    public NumberSource<T> rem(NumberSource<T> source);

    /**
     * Absolute value of this numeric source.
     *
     * @return a {@link NumberSource} producing the absolute value
     */
    public NumberSource<T> abs();

    /**
     * Negates the numeric source (unary minus).
     *
     * @return a {@link NumberSource} producing the negated value
     */
    public NumberSource<T> negate();

    /**
     * Returns the sign of the numeric value (-1, 0 or 1) as the same numeric
     * type where applicable.
     *
     * @return a {@link NumberSource} producing the sign
     */
    public NumberSource<T> sign();
}
