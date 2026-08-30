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

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Grouped list result produced by {@link ListSource#group(java.util.function.Function)}.
 *
 * @param <K> key type for each group entry
 * @param <T> element type contained in each grouped list
 */
public interface Group<K, T> extends ListSource<Pair<K, List<T>>> {
}
