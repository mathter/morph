package io.github.mathter.jmorph.dsl;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Grouped list result produced by {@link ListSource#group(java.util.function.Function)}.
 *
 * @param <K> key type for each group entry
 * @param <T> element type contained in each grouped list
 * @options none
 * @packages io.github.mathter.jmorph.dsl
 */
public interface Group<K, T> extends ListSource<Pair<K, List<T>>> {
}
