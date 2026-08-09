package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.path.Path

/**
 * Convenience base class for listeners providing constructor parameters
 * for root and related paths.
 *
 * @param root top-level path this listener is interested in
 * @param related related paths to include when producing the listener's
 *                PathMap
 */
abstract class AbstractListener(val root: Path, val related: Set[Path]) extends Listener {

}
