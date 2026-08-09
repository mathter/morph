package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.processing.xml.simple.{XmlParser, XmlParserFactory}

import javax.xml.parsers.SAXParserFactory

/**
 * Factory producing XmlParser instances using a configured
 * javax.xml.parsers.SAXParserFactory.
 *
 * @param saxParserFactory factory used to create SAXParser instances
 */
class ParserFactory(private val saxParserFactory: SAXParserFactory) extends XmlParserFactory {
  /**
   * Create a new XmlParser backed by a SAXParser from the configured
   * factory.
   */
  override def xmlParser: XmlParser = {
    val saxParser = this.saxParserFactory.newSAXParser()
    new SaxParser(saxParser)
  }
}

object ParserFactory {
  /**
   * Create a ParserFactory with the default SAXParserFactory.
   */
  def newInstance(): ParserFactory = this.newInstance(SAXParserFactory.newInstance())

  /**
   * Create a ParserFactory configured for namespace-aware parsing.
   */
  def newNSInstance(): ParserFactory = this.newInstance(SAXParserFactory.newNSInstance())

  /**
   * Create a ParserFactory wrapping the provided SAXParserFactory.
   */
  def newInstance(saxParserFactory: SAXParserFactory): ParserFactory = new ParserFactory(saxParserFactory)
}
