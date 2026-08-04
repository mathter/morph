package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;

public interface StringSource extends Source<String> {
    public StringSource toUpperCase();

    public StringSource toLowerCase();

    public StringSource replaceAll(String regexpr, String replacement);

    public NumberSource<Integer> length();

    public StringSource trim();

    public BooleanSource isEmpty();

    public BooleanSource notEmpty();

    public BooleanSource isBlank();

    public BooleanSource notBlank();
}