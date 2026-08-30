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
package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path

/**
 * Listener interface invoked when an XML root element (and its related
 * sub-elements) has been parsed into a PathMap.
 */
trait Listener {
  /**
   * The root Path that this listener is interested in.
   */
  def root: Path

  /**
   * Related Paths whose content should also be collected and provided to
   * the listener when the root element is complete.
   */
  def related: Set[Path]

  /**
   * Called with the PathMap representing the parsed data for the
   * listener's root and related entries.
   *
   * @param pathMap collected data
   */
  def apply(pathMap: PathMap): Unit
}
