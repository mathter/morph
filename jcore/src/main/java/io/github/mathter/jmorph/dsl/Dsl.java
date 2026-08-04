package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.LiteralDsl;
import io.github.mathter.morph.dsl.OriginDsl;
import io.github.mathter.morph.dsl.ResultDsl;
import io.github.mathter.morph.dsl.Source;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;

public interface Dsl extends
        LiteralDsl,
        OriginDsl,
        ResultDsl {
    public StringSource asStringSource(Source<String> source);

    public NumberSource<Byte> asByteSource(Source<Byte> source);

    public NumberSource<Short> asShortSource(Source<Short> source);

    public NumberSource<Integer> asIntSource(Source<Integer> source);

    public NumberSource<Long> asLongSource(Source<Long> source);

    public NumberSource<Float> asFloatSource(Source<Float> source);

    public NumberSource<Double> asDoubleSource(Source<Double> source);

    public NumberSource<BigInteger> asBigIntegerSource(Source<BigInteger> source);

    public NumberSource<BigDecimal> asBigDecimalSource(Source<BigDecimal> source);

    public <T> ListSource<T> asListSource(Source<List<T>> source);

    public BooleanSource asBooleanSource(Source<Boolean> source);

    public NumberSource<Byte> literal(Byte value);

    public NumberSource<Short> literal(Short value);

    public NumberSource<Integer> literal(Integer value);

    public NumberSource<Long> literal(Long value);

    public NumberSource<Float> literal(Float value);

    public NumberSource<Double> literal(Double value);

    public NumberSource<BigInteger> literal(BigInteger value);

    public NumberSource<BigDecimal> literal(BigDecimal value);

    public <T extends Number> NumberSource<T> numberLiteral(Supplier<T> supplier);

    public StringSource literal(String value);

    public StringSource stringLiteral(Supplier<String> supplier);

    public BooleanSource literal(Boolean literal);

    public BooleanSource booleanLiteral(Supplier<Boolean> supplier);

    public <T> ListSource<T> literal(List<T> literal);

    public <T> T first(Source<List<T>> source);
}
