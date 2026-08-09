package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.processing.xml.sax.{Listener, Transformer}
import org.xml.sax.InputSource

import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import scala.collection.mutable

/**
 * SAX-based XML transformer that registers listeners and resolves XML
 * Sources by parsing them and invoking listeners with parsed PathMaps.
 *
 * @param saxParserFactory factory used to obtain SAXParser instances
 */
class SaxXmlTransformer(private val saxParserFactory: SAXParserFactory) extends Transformer {
  private val listeners: mutable.Set[Listener] = mutable.Set()

  /**
   * Add one or more listeners that will receive parsed PathMap events.
   *
   * @param listeners listeners to register
   */
  override def addListener(listeners: Listener*): Unit = {
    this.listeners.addAll(listeners)
  }

  /**
   * Remove a previously registered listener.
   *
   * @param listener listener to remove
   */
  override def removeListener(listener: Listener): Unit =
    this.listeners.remove(listener)

  /**
   * Resolve the given javax.xml.transform.Source by parsing it with a
   * SAX parser and dispatching events to registered listeners.
   *
   * Only StreamSource instances are supported; other Source types will
   * produce an IllegalStateException.
   *
   * @param source XML source to resolve
   */
  override def resolve(source: Source): Unit = {
    val saxParser = this.saxParserFactory.newSAXParser()
    val handler = new SaxHandler(this.listeners.toSet)

    source match {
      case s: StreamSource => {
        val inputSource = InputSource(s.getInputStream)
        inputSource.setSystemId(s.getSystemId)
        inputSource.setPublicId(s.getPublicId)

        saxParser.parse(inputSource, handler)
      }
      case _ => throw new IllegalStateException(s"Illegal source ${source}!")
    }
  }
}
