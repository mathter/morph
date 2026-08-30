// Copyright (c) 2026 mathter
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, without
// warranties or condition of any kind. You may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// All rights reserved.
package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path
import io.github.mathter.morph.processing.xml.trimToNull
import org.xml.sax.Attributes
import org.xml.sax.ext.DefaultHandler2

import scala.collection.mutable

/**
 * Simple SAX handler that builds a PathMap representation while parsing
 * XML events.
 *
 * The handler keeps stacks of current Path and PathMap instances and
 * collects character content for leaf elements. It is intended for
 * lightweight XML-to-PathMap parsing (simple use-cases without namespaces
 * complexity).
 */
class SaxHandler extends DefaultHandler2 {

  /** Current nesting level (root document starts at 0 after first element). */
  protected var level = -1

  /**
   * Cached Path instances for each nesting level keyed by (qName, uri).
   * This avoids recreating Path objects for repeated element names.
   */
  protected val pathByLavel: mutable.Map[Int, mutable.Map[(String, String), Path]] = mutable.Map.empty

  /** Stack of paths representing the current element ancestry. */
  protected val pathStack: mutable.Stack[Path] = mutable.Stack.empty

  /** Stack of PathMap builders for nested elements. */
  protected val pathMapStack: mutable.Stack[PathMap] = mutable.Stack.empty

  /** Accumulated character content for the current element, or null
    * when the element is expected to contain nested elements instead. */
  protected var content: String = null

  /**
   * Resulting PathMap after parsing the document. The implementation
   * stores the result at the top of the pathMapStack.
   */
  def result: PathMap = this.pathMapStack.head

  /**
   * Clear internal parser state so the handler can be reused.
   */
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
