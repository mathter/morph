package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path
import io.github.mathter.morph.processing.xml.sax.Listener
import org.slf4j.LoggerFactory
import org.xml.sax.ext.DefaultHandler2
import org.xml.sax.{Attributes, Locator}

import scala.collection.{immutable, mutable}

private class SaxHandler extends DefaultHandler2 {
  val handlers: mutable.Set[Handler] = mutable.Set()

  private val map: mutable.Map[Path, Set[Handler]] = mutable.Map()

  private val stack: mutable.Stack[PathMap] = mutable.Stack()

  private var level = -1

  private var locator: Locator = _

  private var systemId: String = _

  private var publicId: String = _

  private var path: Path = _

  def this(listeners: Set[Listener]) = {
    this()
    this.handlers.addAll(listeners.map(Handler(_)))
  }

  override def startDocument(): Unit = {
    SaxHandler.log.info("Start parsing publicId={}, systemId={}", this.publicId, this.systemId)
  }

  override def endDocument(): Unit = {
    SaxHandler.log.info("Complete parsing publicId={}, systemId={}", this.publicId, this.systemId)
  }

  override def startElement(uri: String, localName: String, qName: String, attributes: Attributes): Unit = {
    this.level += 1
    this.path(uri, qName)

    this.map.getOrElseUpdate(
        this.path,
        this.handlers
          .filter(handler =>
            handler.root.isParentOf(this.path)
              || handler.related.exists(_.isParentOf(this.path)))
          .toSet
      )
      .foreach(_.startElement(this.path))
  }

  override def endElement(uri: String, localName: String, qName: String): Unit = {
    this.map.getOrElse(this.path, mutable.Set[Handler]())
      .foreach(_.endElement(this.path))

    this.level -= 1
    this.path = this.path.parent
  }

  override def characters(ch: Array[Char], start: Int, length: Int): Unit = {
    this.map.getOrElse(this.path, Set[Handler]())
      .foreach(_.characters(ch, start, length))
  }

  override def setDocumentLocator(locator: Locator): Unit = {
    this.locator = locator;
    this.systemId = locator.getSystemId
    this.publicId = locator.getPublicId
  }

  inline def path(uri: String, name: String): Unit = {
    val fullName = if (uri != null) {
      if (uri.isBlank) {
        name.trim
      } else {
        uri.trim + ":" + name.trim
      }
    } else {
      name.trim
    }

    this.path = if (this.path == null) fullName else this.path.path(fullName)
  }
}

object SaxHandler {
  private val log = LoggerFactory.getLogger(SaxHandler.getClass)
}
