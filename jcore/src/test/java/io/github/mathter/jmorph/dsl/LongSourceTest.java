package io.github.mathter.jmorph.dsl;

import io.github.mathter.jmorph.dsl.base.BaseDsl;
import io.github.mathter.morph.data.PathMap;
import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.base.eval.BaseContext;
import io.github.mathter.morph.dsl.base.eval.Evaluator;
import io.github.mathter.morph.eval.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongSourceTest {
    @Test
    public void testLongPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(10L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> r = dsl.literal(20L);
        Assertions.assertNotNull(r);
        final NumberSource<Long> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongIntegerPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.numberLiteral(() -> 10L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> r = dsl.numberLiteral(() -> 20L);
        Assertions.assertNotNull(r);
        final NumberSource<Long> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongMinus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(10L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> r = dsl.literal(20L);
        Assertions.assertNotNull(r);
        final NumberSource<Long> s = l.minus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(-10L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongMultiply() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(10L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> r = dsl.literal(20L);
        Assertions.assertNotNull(r);
        final NumberSource<Long> s = l.multiply(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(200L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongDivide() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(30L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> r = dsl.literal(20L);
        Assertions.assertNotNull(r);
        final NumberSource<Long> s = l.divide(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(1L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongRem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(30L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> r = dsl.literal(20L);
        Assertions.assertNotNull(r);
        final NumberSource<Long> s = l.rem(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(10L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongAbs() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(-30L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongNegate() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(-30L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(30L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongSignLeft() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(-30L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(-1L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongSignZero() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(0L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(0L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLongSignRight() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<Long> l = dsl.literal(10L);
        Assertions.assertNotNull(l);
        final NumberSource<Long> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(1L, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAsLongSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final long origin = 10;
        final NumberSource<Long> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<Long> r = dsl.asLongSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsLongSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final long origin = 10;
        final Source<Long> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final NumberSource<Long> r = dsl.asLongSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
