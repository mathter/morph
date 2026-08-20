package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path
import io.github.mathter.morph.processing.xml.sax.Listener
import io.github.mathter.morph.processing.xml.sax.Loading.{NONE, RELATED, ROOT}

import scala.collection.mutable

private class Handler(private val listener: Listener) {
  /** Stack used to build the root PathMap while parsing nested elements. */
  private val rootStack: mutable.Stack[PathMap] = mutable.Stack()

  /** Temporarily stores related elements until the root is complete. */
  private var relatedPathMap: PathMap = _

  /** Accumulated character value for the current element. */
  private var value: String = _

  /** Current loading state (NONE, RELATED or ROOT). */
  private var loading = Loading.NONE

  /** Listener's root path. */
  inline def root: Path = this.listener.root

  /** Listener's related paths. */
  inline def related: Set[Path] = this.listener.related

  /**
   * Handle the start of an element at the given path. Updates internal
   * stacks/state and prepares to collect nested content.
   *
   * @param path element absolute path
   */
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

  /**
   * Handle the end of an element. Depending on the loading state the
   * collected value or nested PathMap is attached to the current root
   * representation and the listener is invoked when the root completes.
   *
   * @param path element absolute path
   */
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

  /** Append character data for the current element. */
  def characters(ch: Array[Char], start: Int, length: Int): Unit = {
    if (this.value != null) {
      this.value += String(ch, start, length)
    }
  }
}