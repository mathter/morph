package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.OriginDsl;
import io.github.mathter.morph.dsl.ResultDsl;
import io.github.mathter.morph.dsl.Source;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;

/**
 * DSL factory and helper interface for jmorph transformation expressions.
 * <p>
 * Provides methods to adapt plain Source<T> instances into richer,
 * type-specific source interfaces (StringSource, NumberSource, ListSource,
 * BooleanSource and Group) and to create literal sources from values or
 * lazily-evaluated suppliers. Implementations bridge the underlying
 * morph.dsl.Source primitives and the higher-level jmorph DSL used by
 * transformations.
 */
public interface Dsl extends
        OriginDsl,
        ResultDsl {
    /**
     * Wraps a plain {@code Source<String>} into a {@link StringSource}
     * that exposes string-specific DSL operations.
     *
     * @param source the underlying string source
     * @return a {@link StringSource} view of the provided source
     */
    public StringSource asStringSource(Source<String> source);

    /**
     * Wraps a {@code Source<Byte>} into a {@link NumberSource} for byte
     * arithmetic operations.
     *
     * @param source the byte source
     * @return a byte {@link NumberSource}
     */
    public NumberSource<Byte> asByteSource(Source<Byte> source);

    /**
     * Wraps a {@code Source<Short>} into a {@link NumberSource} for short
     * arithmetic operations.
     *
     * @param source the short source
     * @return a short {@link NumberSource}
     */
    public NumberSource<Short> asShortSource(Source<Short> source);

    /**
     * Wraps a {@code Source<Integer>} into a {@link NumberSource}.
     *
     * @param source the integer source
     * @return an integer {@link NumberSource}
     */
    public NumberSource<Integer> asIntSource(Source<Integer> source);

    /**
     * Wraps a {@code Source<Long>} into a {@link NumberSource}.
     *
     * @param source the long source
     * @return a long {@link NumberSource}
     */
    public NumberSource<Long> asLongSource(Source<Long> source);

    /**
     * Wraps a {@code Source<Float>} into a {@link NumberSource}.
     *
     * @param source the float source
     * @return a float {@link NumberSource}
     */
    public NumberSource<Float> asFloatSource(Source<Float> source);

    /**
     * Wraps a {@code Source<Double>} into a {@link NumberSource}.
     *
     * @param source the double source
     * @return a double {@link NumberSource}
     */
    public NumberSource<Double> asDoubleSource(Source<Double> source);

    /**
     * Wraps a {@code Source<BigInteger>} into a {@link NumberSource}.
     *
     * @param source the big-integer source
     * @return a big-integer {@link NumberSource}
     */
    public NumberSource<BigInteger> asBigIntegerSource(Source<BigInteger> source);

    /**
     * Wraps a {@code Source<BigDecimal>} into a {@link NumberSource}.
     *
     * @param source the big-decimal source
     * @return a big-decimal {@link NumberSource}
     */
    public NumberSource<BigDecimal> asBigDecimalSource(Source<BigDecimal> source);

    /**
     * Wraps a {@code Source<List<T>>} into a {@link ListSource} to access
     * list-specific operations.
     *
     * @param source the list source
     * @param <T> element type
     * @return a {@link ListSource} for the given element type
     */
    public <T> ListSource<T> asListSource(Source<List<T>> source);

    /**
     * Wraps a {@code Source<Boolean>} into a {@link BooleanSource} to use
     * logical combinators.
     *
     * @param source the boolean source
     * @return a {@link BooleanSource}
     */
    public BooleanSource asBooleanSource(Source<Boolean> source);

    /**
     * Creates a constant {@link io.github.mathter.morph.dsl.Source} from a
     * value.
     *
     * @param value the literal value
     * @param <T>   value type
     * @return a constant Source producing {@code value}
     */
    public <T> Source<T> literal(T value);

    /**
     * Convenience overload to create a byte literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<Byte> literal(Byte value);

    /**
     * Convenience overload to create a short literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<Short> literal(Short value);

    /**
     * Convenience overload to create an integer literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<Integer> literal(Integer value);

    /**
     * Convenience overload to create a long literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<Long> literal(Long value);

    /**
     * Convenience overload to create a float literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<Float> literal(Float value);

    /**
     * Convenience overload to create a double literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<Double> literal(Double value);

    /**
     * Convenience overload to create a BigInteger literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<BigInteger> literal(BigInteger value);

    /**
     * Convenience overload to create a BigDecimal literal wrapped as a
     * {@link NumberSource}.
     */
    public NumberSource<BigDecimal> literal(BigDecimal value);

    /**
     * Creates a lazily-evaluated numeric literal using a supplier.
     *
     * @param supplier supplier producing the numeric value on evaluation
     * @param <T>      numeric type
     * @return a {@link NumberSource} backed by the supplier
     */
    public <T extends Number> NumberSource<T> numberLiteral(Supplier<T> supplier);

    /**
     * Convenience overload to create a string literal source.
     */
    public StringSource literal(String value);

    /**
     * Creates a lazily-evaluated string literal from a supplier.
     *
     * @param supplier supplier producing the string when evaluated
     * @return a {@link StringSource}
     */
    public StringSource stringLiteral(Supplier<String> supplier);

    /**
     * Convenience overload to create a boolean literal wrapped as a
     * {@link BooleanSource}.
     */
    public BooleanSource literal(Boolean literal);

    /**
     * Creates a lazily-evaluated boolean literal from a supplier.
     *
     * @param supplier supplier producing the boolean when evaluated
     * @return a {@link BooleanSource}
     */
    public BooleanSource booleanLiteral(Supplier<Boolean> supplier);

    /**
     * Creates a constant list source from an immutable list value.
     *
     * @param literal list to be used as the constant source
     * @param <T>     element type
     * @return a {@link ListSource} producing the provided list
     */
    public <T> ListSource<T> literal(List<T> literal);

    /**
     * Creates a lazily-evaluated list source from a supplier.
     *
     * @param supplier supplier producing the list when evaluated
     * @param <T>      element type
     * @return a {@link ListSource}
     */
    public <T> ListSource<T> listLiteral(Supplier<List<T>> supplier);
}
