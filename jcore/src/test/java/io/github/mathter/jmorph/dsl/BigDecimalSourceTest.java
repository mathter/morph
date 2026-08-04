package io.github.mathter.jmorph.dsl;

import io.github.mathter.jmorph.dsl.base.BaseDsl;
import io.github.mathter.morph.data.PathMap;
import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.base.eval.BaseContext;
import io.github.mathter.morph.dsl.base.eval.Evaluator;
import io.github.mathter.morph.eval.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BigDecimalSourceTest {
    @Test
    public void testPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> r = dsl.literal(BigDecimal.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigDecimal> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSupplierPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.numberLiteral(() -> BigDecimal.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> r = dsl.numberLiteral(() -> BigDecimal.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigDecimal> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMinus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> r = dsl.literal(BigDecimal.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigDecimal> s = l.minus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(-10), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMultiply() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> r = dsl.literal(BigDecimal.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigDecimal> s = l.multiply(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(200), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testDivide() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(30));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> r = dsl.literal(BigDecimal.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigDecimal> s = l.divide(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(1.5), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testRem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(30));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> r = dsl.literal(BigDecimal.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigDecimal> s = l.rem(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(10), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAbs() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(-30));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testNegate() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(-30));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignfeft() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(-30));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.valueOf(-1), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignZero() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.ZERO);
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.ZERO, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignRight() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigDecimal> l = dsl.literal(BigDecimal.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigDecimal> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigDecimal.ONE, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAsBigDecimalSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final BigDecimal origin = new BigDecimal(10);
        final NumberSource<BigDecimal> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<BigDecimal> r = dsl.asBigDecimalSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsBigDecimalSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BigDecimal origin = new BigDecimal(10);
        final Source<BigDecimal> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final NumberSource<BigDecimal> r = dsl.asBigDecimalSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
