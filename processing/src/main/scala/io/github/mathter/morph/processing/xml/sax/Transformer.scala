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

import javax.xml.transform.Source

/**
 * Transformer contract for resolving XML Sources and notifying
 * registered listeners with parsed PathMap data.
 */
trait Transformer {
  /**
   * Register one or more listeners to receive parsed events.
   *
   * @param listener listeners to register
   */
  def addListener(listener: Listener*): Unit

  /**
   * Unregister a previously registered listener.
   *
   * @param listener listener to remove
   */
  def removeListener(listener: Listener): Unit

  /**
   * Resolve the provided XML Source by parsing and dispatching to
   * listeners.
   *
   * @param source XML Source to resolve
   */
  def resolve(source: Source): Unit
}
