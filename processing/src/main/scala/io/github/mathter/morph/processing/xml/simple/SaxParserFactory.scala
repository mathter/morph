package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.processing.xml.simple.{XmlParser, XmlParserFactory}

import javax.xml.parsers.SAXParserFactory

class ParserFactory(private val saxParserFactory: SAXParserFactory) extends XmlParserFactory {
  override def xmlParser: XmlParser = {
    val saxParser = this.saxParserFactory.newSAXParser()
    new SaxParser(saxParser)
  }
}

object ParserFactory {
  def newInstance(): ParserFactory = this.newInstance(SAXParserFactory.newInstance())

  def newNSInstance(): ParserFactory = this.newInstance(SAXParserFactory.newNSInstance())

  def newInstance(saxParserFactory: SAXParserFactory): ParserFactory = new ParserFactory(saxParserFactory)
}
