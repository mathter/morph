package io.github.mathter.morph.processing.ext

import io.github.mathter.morph.path.Path

abstract class AbstractListener(val root: Path, val related: Set[Path]) extends Listener {

}
