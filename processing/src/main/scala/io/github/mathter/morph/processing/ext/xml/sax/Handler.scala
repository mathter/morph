package io.github.mathter.morph.processing.ext.xml.sax

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path
import io.github.mathter.morph.processing.ext.Listener
import io.github.mathter.morph.processing.ext.xml.sax.Loading.{NONE, RELATED, ROOT}
import org.xml.sax.Attributes

import scala.collection.mutable

private class Handler(private val listener: Listener) {
  private val rootStack: mutable.Stack[PathMap] = mutable.Stack()

  private var relatedPathMap: PathMap = _

  private var value: String = _

  private var loading = Loading.NONE

  inline def root: Path = this.listener.root

  inline def related: Set[Path] = this.listener.related

  def startElement(path: Path): Unit = {
    if (path equals this.listener.root) {
      this.loading = ROOT
      this.rootStack.push(PathMap.empty)
    } else if (this.listener.related.contains(path) && this.loading == NONE) {
      this.loading = RELATED
      this.relatedPathMap = PathMap.empty
    }

    if (this.loading == ROOT) {
      this.rootStack.push(PathMap.empty)
    }

    this.value = ""
  }

  def endElement(path: Path): Unit = {
    this.loading match {
      case ROOT => {
        if (this.value != null) {
          this.rootStack.pop()
          this.rootStack.top.put(path.local, this.value)
        } else {
          val pathMap = this.rootStack.pop();
          this.rootStack.top.put(path.local, pathMap)
        }
      }
      case RELATED =>
        if (this.value != null) {
          this.listener.related
            .map(_.parent.relativize(path))
            .find(_ != null)
            .foreach(p => this.relatedPathMap.put(p, value))
        }
    }

    if (this.rootStack.length == 1 && this.loading == ROOT) {
      this.loading = NONE

      val pathMap = this.rootStack.pop()

      if (this.relatedPathMap != null) {
        this.relatedPathMap.entries
          .foreach(e => pathMap.put(e._1, e._2))
      }

      this.listener.apply(pathMap)
    }

    this.value = null
  }

  def characters(ch: Array[Char], start: Int, length: Int): Unit = {
    if (this.value != null) {
      this.value += String(ch, start, length)
    }
  }
}