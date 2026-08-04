package io.github.mathter.morph.processing.xml

import io.github.mathter.morph.data.PathMap
import org.xml.sax.InputSource

trait XmlParser {
  def parse(is: InputSource): PathMap
}
