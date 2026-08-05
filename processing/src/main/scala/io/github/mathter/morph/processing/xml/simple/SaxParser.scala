package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.processing.xml.simple.XmlParser
import org.xml.sax.InputSource

import javax.xml.parsers.SAXParser

private class SaxParser(private val saxParser: SAXParser) extends XmlParser {
  private val handler = new SaxHandler

  override def parse(is: InputSource): PathMap = {
    this.saxParser.parse(is, this.handler)
    handler.result
  }
}
