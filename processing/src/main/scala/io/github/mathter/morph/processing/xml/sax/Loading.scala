package io.github.mathter.morph.processing.xml.sax

/**
 * Internal loading state used by SAX handlers to track whether the
 * parser is currently collecting a root element, related elements, or
 * nothing.
 */
private enum Loading {
  case NONE
  case RELATED
  case ROOT
}