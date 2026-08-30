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
package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.Then;

/**
 * Boolean-valued source expressions used in jmorph DSL.
 * <p>
 * Adds logical combinators (and/or/xor/not) and a {@code then} terminal
 * to chain side-effecting or conditional continuations.
 */
public interface BooleanSource extends Source<Boolean> {
    /**
     * Logical conjunction of this boolean source with another boolean
     * source.
     *
     * @param other the other boolean source
     * @return a {@link BooleanSource} representing the conjunction
     */
    public BooleanSource and(Source<Boolean> other);

    /**
     * Logical disjunction of this boolean source with another boolean
     * source.
     *
     * @param other the other boolean source
     * @return a {@link BooleanSource} representing the disjunction
     */
    public BooleanSource or(Source<Boolean> other);

    /**
     * Logical exclusive-or between this and another boolean source.
     *
     * @param other the other boolean source
     * @return a {@link BooleanSource} representing the xor
     */
    public BooleanSource xor(Source<Boolean> other);

    /**
     * Logical negation of this boolean source.
     *
     * @return a {@link BooleanSource} yielding the negated boolean value
     */
    public BooleanSource not();

    /**
     * Conditional continuation: when this boolean source evaluates to true,
     * the provided {@code source} is evaluated and returned via a
     * {@link Then} construct.
     *
     * @param source continuation source executed when predicate is true
     * @param <T>    result type of the continuation
     * @return a {@link Then} describing the conditional continuation
     */
    public <T> Then<T> then(Source<T> source);
}
