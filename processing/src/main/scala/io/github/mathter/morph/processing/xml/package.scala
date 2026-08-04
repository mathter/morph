package io.github.mathter.morph.processing

package object xml {
  extension (x: String) {
    inline def trimToNull: String = {
      if (x != null && "" == x.trim) {
        null
      } else {
        x
      }
    }
  }
}
