package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.processing.xml.sax.{Listener, Transformer}
import org.xml.sax.InputSource

import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import scala.collection.mutable

class SaxXmlTransformer(private val saxParserFactory: SAXParserFactory) extends Transformer {
  private val listeners: mutable.Set[Listener] = mutable.Set()

  override def addListener(listeners: Listener*): Unit = {
    this.listeners.addAll(listeners)
  }

  override def removeListener(listener: Listener): Unit =
    this.listeners.remove(listener)

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
