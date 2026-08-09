package io.github.mathter.morph.processing

package object xml {
  /**
   * String extension helpers used by XML processing.
   */
  extension (x: String) {
    /**
     * Trim the string and return null when it becomes empty.
     *
     * This helper is convenient for normalizing XML namespace URIs and
     * local names where empty strings should be treated as absent (null).
     *
     * @return trimmed string or null if the trimmed value is empty
     */
    inline def trimToNull: String = {
      if (x != null && "" == x.trim) {
        null
      } else {
        x
      }
    }
  }
}
