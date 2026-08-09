package io.github.mathter.morph.processing.xml.sax

import javax.xml.transform.Source

/**
 * Transformer contract for resolving XML Sources and notifying
 * registered listeners with parsed PathMap data.
 */
trait Transformer {
  /**
   * Register one or more listeners to receive parsed events.
   *
   * @param listener listeners to register
   */
  def addListener(listener: Listener*): Unit

  /**
   * Unregister a previously registered listener.
   *
   * @param listener listener to remove
   */
  def removeListener(listener: Listener): Unit

  /**
   * Resolve the provided XML Source by parsing and dispatching to
   * listeners.
   *
   * @param source XML Source to resolve
   */
  def resolve(source: Source): Unit
}
