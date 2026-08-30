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

public class ShortSourceTest {
    @Test
    public void testPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 10);
        Assertions.assertNotNull(l);
        final NumberSource<Short> r = dsl.literal((short) 20);
        Assertions.assertNotNull(r);
        final NumberSource<Short> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSupplierPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.numberLiteral(() -> (short) 10);
        Assertions.assertNotNull(l);
        final NumberSource<Short> r = dsl.numberLiteral(() -> (short) 20);
        Assertions.assertNotNull(r);
        final NumberSource<Short> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMinus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 10);
        Assertions.assertNotNull(l);
        final NumberSource<Short> r = dsl.literal((short) 20);
        Assertions.assertNotNull(r);
        final NumberSource<Short> s = l.minus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) -10, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMultiply() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 10);
        Assertions.assertNotNull(l);
        final NumberSource<Short> r = dsl.literal((short) 20);
        Assertions.assertNotNull(r);
        final NumberSource<Short> s = l.multiply(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 200, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testDivide() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 30);
        Assertions.assertNotNull(l);
        final NumberSource<Short> r = dsl.literal((short) 20);
        Assertions.assertNotNull(r);
        final NumberSource<Short> s = l.divide(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testRem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 30);
        Assertions.assertNotNull(l);
        final NumberSource<Short> r = dsl.literal((short) 20);
        Assertions.assertNotNull(r);
        final NumberSource<Short> s = l.rem(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 10, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAbs() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) -30);
        Assertions.assertNotNull(l);
        final NumberSource<Short> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testNegate() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) -30);
        Assertions.assertNotNull(l);
        final NumberSource<Short> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 30, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignLeft() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) -30);
        Assertions.assertNotNull(l);
        final NumberSource<Short> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) -1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignZero() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 0);
        Assertions.assertNotNull(l);
        final NumberSource<Short> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 0, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignRight() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Short> l = dsl.literal((short) 10);
        Assertions.assertNotNull(l);
        final NumberSource<Short> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals((short) 1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAsShortSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final short origin = 10;
        final NumberSource<Short> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<Short> r = dsl.asShortSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsShortSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final short origin = 10;
        final Source<Short> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final NumberSource<Short> r = dsl.asShortSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
