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

public class DoubleSourceTest {
    @Test
    public void testPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(10d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> r = dsl.literal(20d);
        Assertions.assertNotNull(r);
        final NumberSource<Double> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSupplierPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.numberLiteral(() -> 10d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> r = dsl.numberLiteral(() -> 20d);
        Assertions.assertNotNull(r);
        final NumberSource<Double> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMinus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(10d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> r = dsl.literal(20d);
        Assertions.assertNotNull(r);
        final NumberSource<Double> s = l.minus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(-10f, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMultiply() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(10d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> r = dsl.literal(20d);
        Assertions.assertNotNull(r);
        final NumberSource<Double> s = l.multiply(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(200d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testDivide() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(30d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> r = dsl.literal(20d);
        Assertions.assertNotNull(r);
        final NumberSource<Double> s = l.divide(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(1.5d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testRem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(30d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> r = dsl.literal(20d);
        Assertions.assertNotNull(r);
        final NumberSource<Double> s = l.rem(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(10d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAbs() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(-30d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testNegate() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(-30d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignfeft() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(-30d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(-1d, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignZero() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(0d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(0f, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignRight() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Double> l = dsl.literal(10d);
        Assertions.assertNotNull(l);
        final NumberSource<Double> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(1f, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAsDoubleSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final double origin = 10;
        final NumberSource<Double> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<Double> r = dsl.asDoubleSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsDoubleSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final double origin = 10;
        final Source<Double> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final NumberSource<Double> r = dsl.asDoubleSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
