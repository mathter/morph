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
package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.data.PathMap
import org.xml.sax.InputSource

/**
 * Simple XML parser contract converting an InputSource into a PathMap.
 */
trait XmlParser {
  /**
   * Parse the provided InputSource into a PathMap structure.
   *
   * @param is input source to parse
   * @return parsed PathMap
   */
  def parse(is: InputSource): PathMap
}
