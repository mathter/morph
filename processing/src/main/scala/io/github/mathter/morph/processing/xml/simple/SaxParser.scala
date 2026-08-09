package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.processing.xml.simple.XmlParser
import org.xml.sax.InputSource

import javax.xml.parsers.SAXParser

/**
 * XmlParser implementation that delegates to a javax SAXParser using the
 * simple {@link SaxHandler} to build a PathMap.
 *
 * @param saxParser underlying SAX parser instance
 */
private class SaxParser(private val saxParser: SAXParser) extends XmlParser {
  private val handler = new SaxHandler

  /**
   * Parse the supplied InputSource and return a PathMap representation.
   *
   * @param is input source to parse
   * @return PathMap constructed from parsed XML
   */
  override def parse(is: InputSource): PathMap = {
    this.saxParser.parse(is, this.handler)
    handler.result
  }
}
