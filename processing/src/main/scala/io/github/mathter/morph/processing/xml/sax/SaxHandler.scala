package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path
import io.github.mathter.morph.processing.xml.trimToNull
import org.xml.sax.Attributes
import org.xml.sax.ext.DefaultHandler2

import scala.collection.mutable

class SaxHandler extends DefaultHandler2 {

  protected var level = -1

  protected val pathByLavel: mutable.Map[Int, mutable.Map[(String, String), Path]] = mutable.Map.empty

  protected val pathStack: mutable.Stack[Path] = mutable.Stack.empty

  protected val pathMapStack: mutable.Stack[PathMap] = mutable.Stack.empty

  protected var content: String = null

  def result: PathMap = this.pathMapStack.head

  def clear(): Unit = {
    this.level = -1
    this.pathByLavel.clear()
    this.pathStack.clear()
    this.pathMapStack.clear()
    this.content = null
  }

  override def startDocument(): Unit = this.pathMapStack.push(PathMap.empty)

  override def startElement(uri: String, localName: String, qName: String, attributes: Attributes): Unit = {
    this.level += 1

    val trimUri = uri.trimToNull
    val path = this.pathByLavel.getOrElseUpdate(this.level, mutable.Map.empty)
      .getOrElseUpdate((qName, trimUri), Path(qName, trimUri))

    this.pathStack.push(path)
    this.pathMapStack.push(PathMap.empty)
    this.content = ""
  }

  override def endElement(uri: String, localName: String, qName: String): Unit = {
    this.level -= 1

    val path = this.pathStack.pop()

    if (this.content != null) {
      this.pathMapStack.pop()
      this.pathMapStack.head.put(path, this.content)
    } else {
      val pathMap = this.pathMapStack.pop()
      this.pathMapStack.head.put(path, pathMap)
    }

    this.content = null
  }

  override def characters(ch: Array[Char], start: Int, length: Int): Unit = {
    if (this.content != null) {
      this.content += new String(ch, start, length)
    }
  }
}
