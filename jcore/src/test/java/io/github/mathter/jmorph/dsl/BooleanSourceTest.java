package io.github.mathter.jmorph.dsl;

import io.github.mathter.jmorph.dsl.base.BaseDsl;
import io.github.mathter.morph.data.PathMap;
import io.github.mathter.morph.dsl.Else;
import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.base.eval.BaseContext;
import io.github.mathter.morph.dsl.base.eval.Evaluator;
import io.github.mathter.morph.eval.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BooleanSourceTest {
    @Test
    public void testIfTrue() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BooleanSource b = dsl.literal(true);
        final StringSource then = dsl.literal("true");
        final StringSource else_ = dsl.literal("false");

        final Else<String> s = b.then(then).Else(else_);
        Assertions.assertNotNull(s);
        Assertions.assertEquals("true", Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testIfFalse() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BooleanSource b = dsl.literal(false);
        final StringSource then = dsl.literal("true");
        final StringSource else_ = dsl.literal("false");

        final Else<String> s = b.then(then).Else(else_);
        Assertions.assertNotNull(s);
        Assertions.assertEquals("false", Evaluator.evalSource(s, context).get());
    }

    @ParameterizedTest
    @MethodSource
    public void testAnd(Boolean left, Boolean right, Boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BooleanSource leftSource = dsl.literal(left);
        final BooleanSource rightSource = dsl.literal(right);

        final BooleanSource result = leftSource.and(rightSource);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, Evaluator.evalSource(result, context).get());
    }

    public static Stream<Arguments> testAnd() {
        return Stream.of(
                Arguments.of(false, false, false),
                Arguments.of(false, true, false),
                Arguments.of(true, false, false),
                Arguments.of(true, true, true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testOr(Boolean left, Boolean right, Boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BooleanSource leftSource = dsl.literal(left);
        final BooleanSource rightSource = dsl.literal(right);

        final BooleanSource result = leftSource.or(rightSource);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, Evaluator.evalSource(result, context).get());
    }

    public static Stream<Arguments> testOr() {
        return Stream.of(
                Arguments.of(false, false, false),
                Arguments.of(false, true, true),
                Arguments.of(true, false, true),
                Arguments.of(true, true, true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testXor(Boolean left, Boolean right, Boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BooleanSource leftSource = dsl.literal(left);
        final BooleanSource rightSource = dsl.literal(right);

        final BooleanSource result = leftSource.xor(rightSource);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, Evaluator.evalSource(result, context).get());
    }

    public static Stream<Arguments> testXor() {
        return Stream.of(
                Arguments.of(false, false, false),
                Arguments.of(false, true, true),
                Arguments.of(true, false, true),
                Arguments.of(true, true, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testNot(Boolean origin, Boolean expected) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final BooleanSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final BooleanSource result = s.not();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, Evaluator.evalSource(result, context).get());
    }

    public static Stream<Arguments> testNot() {
        return Stream.of(
                Arguments.of(true, false),
                Arguments.of(false, true)
        );
    }

    @Test
    public void testAsBooleanSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final boolean origin = true;
        final BooleanSource s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final BooleanSource r = dsl.asBooleanSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsBooleanSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final boolean origin = true;
        final Source<Boolean> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final BooleanSource r = dsl.asBooleanSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
