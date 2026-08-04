package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;

public interface NumberSource<T extends Number> extends Source<T> {
    public NumberSource<T> plus(NumberSource<T> source);

    public NumberSource<T> minus(NumberSource<T> source);

    public NumberSource<T> multiply(NumberSource<T> source);

    public NumberSource<T> divide(NumberSource<T> source);

    public NumberSource<T> rem(NumberSource<T> source);

    public NumberSource<T> abs();

    public NumberSource<T> negate();

    public NumberSource<T> sign();
}
