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

import io.github.mathter.jmorph.dsl.base.BaseDsl;
import io.github.mathter.morph.data.PathMap;
import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.base.BaseContext;
import io.github.mathter.morph.dsl.base.Evaluator;
import io.github.mathter.morph.eval.Context;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class StringSourceTest {
    @Test
    public void testToUpperCase() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final String origin = "Wwert0_ewewE";

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final StringSource r = s.toUpperCase();
        Assertions.assertNotNull(r);

        Assertions.assertEquals(origin.toUpperCase(), Evaluator.evalSource(r, context).get());
    }

    @Test
    public void testToLowerCase() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final String origin = "Wwert0_ewewE";

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final StringSource r = s.toLowerCase();
        Assertions.assertNotNull(r);

        Assertions.assertEquals(origin.toLowerCase(), Evaluator.evalSource(r, context).get());
    }

    @Test
    public void testReplaceAll() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final String origin = "This is a test";

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final StringSource r = s.replaceAll("T\\S*s", "That");
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin.replaceAll("T\\S*s", "That"), Evaluator.evalSource(r, context).get());
    }

    @Test
    public void testLength() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final String origin = "This is a test";

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<Integer> l = s.length();
        Assertions.assertNotNull(l);
        Assertions.assertEquals(origin.length(), Evaluator.evalSource(l, context).get());
    }

    @Test
    public void testTrim() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final String origin = " for trim ";

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final StringSource r = s.trim();
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin.trim(), Evaluator.evalSource(r, context).get());
    }

    @ParameterizedTest
    @MethodSource
    public void testIsEmpty(String origin, boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final BooleanSource r = s.isEmpty();
        Assertions.assertNotNull(r);
        Assertions.assertEquals(expected, Evaluator.evalSource(r, context).get());
    }

    public static Stream<Arguments> testIsEmpty() {
        return Stream.of(
                Arguments.of(RandomStringUtils.insecure().nextAlphabetic(10), false),
                Arguments.of("", true),
                Arguments.of(null, true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testIsNonEmpty(String origin, boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final BooleanSource r = s.notEmpty();
        Assertions.assertNotNull(r);
        Assertions.assertEquals(expected, Evaluator.evalSource(r, context).get());
    }

    public static Stream<Arguments> testIsNonEmpty() {
        return Stream.of(
                Arguments.of(RandomStringUtils.insecure().nextAlphabetic(10), true),
                Arguments.of("", false),
                Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testIsBlank(String origin, boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final BooleanSource r = s.isBlank();
        Assertions.assertNotNull(r);
        Assertions.assertEquals(expected, Evaluator.evalSource(r, context).get());
    }

    public static Stream<Arguments> testIsBlank() {
        return Stream.of(
                Arguments.of(RandomStringUtils.insecure().nextAlphabetic(10), false),
                Arguments.of(" ", true),
                Arguments.of(null, true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testNonEmpty(String origin, boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final BooleanSource r = s.notBlank();
        Assertions.assertNotNull(r);
        Assertions.assertEquals(expected, Evaluator.evalSource(r, context).get());
    }

    public static Stream<Arguments> testNonEmpty() {
        return Stream.of(
                Arguments.of(RandomStringUtils.insecure().nextAlphabetic(10), true),
                Arguments.of("", false),
                Arguments.of(null, false)
        );
    }

    @Test
    public void testAsStringSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final String origin = "This is a test";
        final StringSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final StringSource r = dsl.asStringSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsStringSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final String origin = "This is a test";
        final Source<String> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final StringSource r = dsl.asStringSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
