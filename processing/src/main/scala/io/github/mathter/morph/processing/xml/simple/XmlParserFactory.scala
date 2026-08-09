package io.github.mathter.morph.processing.xml.simple

/**
 * Factory contract for creating {@link XmlParser} instances.
 */
trait XmlParserFactory {
  /**
   * Return an {@link XmlParser} instance.
   *
   * @return XmlParser
   */
  def xmlParser: XmlParser
}
