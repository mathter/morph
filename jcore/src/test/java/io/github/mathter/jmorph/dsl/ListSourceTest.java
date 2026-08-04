package io.github.mathter.jmorph.dsl;

import io.github.mathter.jmorph.dsl.base.BaseDsl;
import io.github.mathter.morph.data.Opt;
import io.github.mathter.morph.data.PathMap;
import io.github.mathter.morph.dsl.Source;
import io.github.mathter.morph.dsl.base.eval.BaseContext;
import io.github.mathter.morph.dsl.base.eval.Evaluator;
import io.github.mathter.morph.eval.Context;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ListSourceTest {
    @Test
    public void testLiteral() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final ListSource<Integer> s = dsl.literal(List.of(1, 2, 3));
        Assertions.assertNotNull(s);
        Assertions.assertEquals(List.of(1, 2, 3), Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testFirst() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final Source<Integer> s = dsl.literal(List.of(1, 2, 3)).first();
        Assertions.assertNotNull(s);
        Assertions.assertEquals(1, Evaluator.evalSource(s, context).get());
    }

    @Test
    public void testLast() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final Source<Integer> s = dsl.literal(List.of(1, 2, 3)).last();
        Assertions.assertNotNull(s);
        Assertions.assertEquals(3, Evaluator.evalSource(s, context).get());
    }

    @ParameterizedTest
    @MethodSource
    public void testIndex(List<Integer> list, int index) {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final Source<Integer> s = dsl.literal(list).index(dsl.literal(index));
        Assertions.assertNotNull(s);
        Assertions.assertEquals(list.get(index), Evaluator.evalSource(s, context).get());
    }

    private static Stream<Arguments> testIndex() {
        final List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        return IntStream.range(0, list.size())
                .mapToObj(i -> Arguments.of(list, i));
    }

    @Test
    public void testMapElem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final List<Integer> origin = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        final ListSource<Integer> s = dsl.literal(origin).mapElem(e -> e + 1);

        Assertions.assertNotNull(s);
        Assertions.assertEquals(
                origin.stream().map(e -> e + 1).collect(Collectors.toList()),
                Evaluator.evalSource(s, context).get()
        );
    }

    @Test
    public void testMapsElem() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final List<Integer> origin = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        final ListSource<Integer> s = dsl.literal(origin)
                .mapsElem(e -> e.map(ee -> ee + 1));

        Assertions.assertNotNull(s);
        Assertions.assertEquals(
                origin.stream().map(e -> e + 1).collect(Collectors.toList()),
                Evaluator.evalSource(s, context).get()
        );
    }

    @Test
    public void testGroup() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final Group<Integer, Integer> s = dsl.literal(List.of(0, 1, 2, 3, 4, 5, 6, 7))
                .group(e -> e.map(ee -> ee % 2));

        Assertions.assertNotNull(s);
        final Opt<List<Pair<Integer, List<Integer>>>> result = Evaluator.evalSource(s, context);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(2, result.get().size());
        Assertions.assertEquals(0, result.get().get(0).getLeft());
        Assertions.assertEquals(List.of(0, 2, 4, 6), result.get().get(0).getRight());
        Assertions.assertEquals(1, result.get().get(1).getLeft());
        Assertions.assertEquals(List.of(1, 3, 5, 7), result.get().get(1).getRight());
    }

    @Test
    public void testApplyGroup() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());

        final ListSource<Integer> s = dsl.literal(List.of(0, 1, 2, 3, 4, 5, 6, 7))
                .group(e -> e.map(ee -> ee % 2))
                .mapElem(Pair::getRight)
                .mapElem(e -> e.stream().mapToInt(i -> i).sum());
        Assertions.assertNotNull(s);

        final Opt<List<Integer>> result = Evaluator.evalSource(s, context);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(2, result.get().size());
        Assertions.assertEquals(12, result.get().get(0));
        Assertions.assertEquals(16, result.get().get(1));
    }

    @Test
    public void testAsListSourceAsIs() {
        final Dsl dsl = new BaseDsl();
        final List<Integer> origin = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        final ListSource<Integer> s = dsl.literal(origin);
        Assertions.assertNotNull(s);

        final ListSource<Integer> r = dsl.asListSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(s, r);
    }

    @Test
    public void testAsListSource() {
        final Dsl dsl = new BaseDsl();
        final Context context = new BaseContext(PathMap.empty());
        final List<Integer> origin = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Source<List<Integer>> s = dsl.literal(origin).as();
        Assertions.assertNotNull(s);

        final ListSource<Integer> r = dsl.asListSource(s);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(origin, Evaluator.evalSource(s, context).get());
    }
}
