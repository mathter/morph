package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.path.Path

abstract class AbstractListener(val root: Path, val related: Set[Path]) extends Listener {

}
