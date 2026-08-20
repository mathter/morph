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
