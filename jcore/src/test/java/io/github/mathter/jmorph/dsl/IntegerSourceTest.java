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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegerSourceTest {
    @Test
    public void testIntegerPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(10);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> r = dsl.literal(20);
        Assertions.assertNotNull(r);
        final NumberSource<Integer> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerSupplierPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.numberLiteral(() -> 10);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> r = dsl.numberLiteral(() -> 20);
        Assertions.assertNotNull(r);
        final NumberSource<Integer> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerMinus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(10);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> r = dsl.literal(20);
        Assertions.assertNotNull(r);
        final NumberSource<Integer> s = l.minus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(-10, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerMultiply() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(10);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> r = dsl.literal(20);
        Assertions.assertNotNull(r);
        final NumberSource<Integer> s = l.multiply(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(200, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerDivide() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(30);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> r = dsl.literal(20);
        Assertions.assertNotNull(r);
        final NumberSource<Integer> s = l.divide(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerRem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(30);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> r = dsl.literal(20);
        Assertions.assertNotNull(r);
        final NumberSource<Integer> s = l.rem(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(10, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerAbs() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(-30);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerNegate() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(-30);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerSignLeft() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(-30);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(-1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerSignZero() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(0);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(0, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIntegerSignRight() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Integer> l = dsl.literal(10);
        Assertions.assertNotNull(l);
        final NumberSource<Integer> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAsIntSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final int origin = 10;
        final NumberSource<Integer> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<Integer> r = dsl.asIntSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsIntSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final int origin = 10;
        final Source<Integer> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final NumberSource<Integer> r = dsl.asIntSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
