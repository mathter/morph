package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;

import java.util.List;
import java.util.function.Function;

public interface ListSource<T> extends Source<List<T>> {
    public Source<T> first();

    public Source<T> last();

    public Source<T> index(Source<Integer> index);

    public <D> ListSource<D> mapElem(Function<? super T, ? extends D> mapper);

    public <D> ListSource<D> mapsElem(Function<Source<T>, Source<D>> mapper);

    public <K> Group<K, T> group(Function<Source<T>, Source<K>> keyMapper);
}
