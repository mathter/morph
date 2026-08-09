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
