package io.github.mathter.jmorph.dsl;

import io.github.mathter.jmorph.dsl.base.BaseDsl;
import io.github.mathter.morph.data.PathMap;
import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.base.BaseContext;
import io.github.mathter.morph.dsl.base.Evaluator;
import io.github.mathter.morph.eval.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class BigIntegerSourceTest {
    @Test
    public void testPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> r = dsl.literal(BigInteger.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigInteger> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSupplierPlus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.numberLiteral(() -> BigInteger.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> r = dsl.numberLiteral(() -> BigInteger.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigInteger> s = l.plus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMinus() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> r = dsl.literal(BigInteger.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigInteger> s = l.minus(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(-10), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testMultiply() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> r = dsl.literal(BigInteger.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigInteger> s = l.multiply(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(200), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testDivide() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(30));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> r = dsl.literal(BigInteger.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigInteger> s = l.divide(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.ONE, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testRem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(30));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> r = dsl.literal(BigInteger.valueOf(20));
        Assertions.assertNotNull(r);
        final NumberSource<BigInteger> s = l.rem(r);
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(10), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAbs() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(-30));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testNegate() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(-30));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> s = l.abs();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(30), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignfeft() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(-30));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.valueOf(-1), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignZero() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.ZERO);
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.ZERO, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testSignRight() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final NumberSource<BigInteger> l = dsl.literal(BigInteger.valueOf(10));
        Assertions.assertNotNull(l);
        final NumberSource<BigInteger> s = l.sign();
        Assertions.assertNotNull(s);

        Assertions.assertEquals(BigInteger.ONE, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testAsBigIntegerSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final BigInteger origin = BigInteger.valueOf(30);
        final NumberSource<BigInteger> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final NumberSource<BigInteger> r = dsl.asBigIntegerSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsBigIntegerSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BigInteger origin = BigInteger.valueOf(30);
        final Source<BigInteger> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final NumberSource<BigInteger> r = dsl.asBigIntegerSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
