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
package io.github.mathter.morph.path

import org.apache.commons.collections4.map.ReferenceMap

import java.util
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.{Lock, ReentrantLock}

private[path] object EPathFactory {
  private val hashCode = AtomicInteger(0)

  private val map: util.Map[(String, String, EPath), EPath] = ReferenceMap()

  private val lock: Lock = new ReentrantLock

  inline def apply(segment: String): EPath = {
    EPathFactory(segment, null)
  }

  inline def apply(segment: String, segmentQ: String): EPath = {
    segment.split(Path.DELIMITER)
      .filter(e => e != null && !"".equals(e))
      .foldLeft[EPath](null)((left, right) => if (left == null) EPathFactory(right, segmentQ, null) else left.path(right))
  }

  inline def apply(segment: String, segmentQ: String, parent: EPath): EPath = {
    this.lock.lock()

    try {
      this.map.computeIfAbsent((segment, segmentQ, parent), key => EPath(segment, segmentQ, parent, this.hashCode.getAndAdd(1)))
    } finally {
      this.lock.unlock()
    }
  }
}
