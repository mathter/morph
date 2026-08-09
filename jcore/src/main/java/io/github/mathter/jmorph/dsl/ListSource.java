package io.github.mathter.jmorph.dsl;

import io.github.mathter.morph.dsl.Source;

import java.util.List;
import java.util.function.Function;

/**
 * List-valued source providing accessors and transformation helpers for
 * list elements.
 * <p>
 * Allows retrieving first/last elements, indexing via a source-derived
 * index, mapping elements (both with direct mappers and mappers that
 * operate on Sources), and grouping elements by a key extractor producing
 * a {@link Group} result.
 *
 * @param <T> element type of the list
 */
public interface ListSource<T> extends Source<List<T>> {
    /**
     * Returns the first element of the list as a {@link Source}.
     * If the list is empty the resulting source should behave according to
     * the underlying implementation's null/absent semantics.
     *
     * @return a {@link Source} for the first element
     */
    public Source<T> first();

    /**
     * Returns the last element of the list as a {@link Source}.
     *
     * @return a {@link Source} for the last element
     */
    public Source<T> last();

    /**
     * Returns an element by index, where the index is provided as a
     * {@link Source<Integer>} (allowing computed indices).
     *
     * @param index source producing the index to read
     * @return a {@link Source} for the indexed element
     */
    public Source<T> index(Source<Integer> index);

    /**
     * Maps each element of the list using a pure function and returns a new
     * {@link ListSource} of the mapped elements.
     *
     * @param mapper mapping function
     * @param <D>    destination element type
     * @return a {@link ListSource} of mapped elements
     */
    public <D> ListSource<D> mapElem(Function<? super T, ? extends D> mapper);

    /**
     * Maps each element using a mapper that accepts element sources and
     * returns element sources. Useful when the mapping itself should use
     * DSL {@link Source}-level operations.
     *
     * @param mapper source-aware mapper
     * @param <D>    destination element type
     * @return a {@link ListSource} of mapped elements
     */
    public <D> ListSource<D> mapsElem(Function<Source<T>, Source<D>> mapper);

    /**
     * Groups elements by a key extracted via the provided source-aware
     * keyMapper, returning a {@link Group} where each element is a pair of
     * key and the list of grouped values.
     *
     * @param keyMapper function producing a key for each element
     * @param <K>       key type
     * @return a {@link Group} keyed by K
     */
    public <K> Group<K, T> group(Function<Source<T>, Source<K>> keyMapper);
}
