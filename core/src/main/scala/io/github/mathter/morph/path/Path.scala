package io.github.mathter.morph.path

/**
 * Represents a hierarchical path composed of segments. Each path node exposes
 * its own segment and an optional segment qualifier (segmentQ), a reference to
 * its parent, and helpers to create or navigate child paths.
 *
 * Paths are immutable and form a tree where each node knows its parent. The
 * trait defines convenience operators (+ and /) that delegate to `path(...)`.
 */
trait Path {
  /** The primary identifier for this path node (the segment). */
  def segment: String;

  /**
   * An optional qualifier or suffix associated with the segment. May be `null`
   * when there is no qualifier.
   */
  def segmentQ: String;

  /** The parent path node. For a root path this may be `null`. */
  def parent: Path;

  /**
   * Create or return a direct child of this path with the given segment.
   *
   * @param seqment the child segment name
   * @return the child Path corresponding to this / seqment
   */
  def path(seqment: String): Path;

  /**
   * Create or return a direct child of this path with the given segment and
   * qualifier.
   *
   * @param seqment the child segment name
   * @param seqmentQ optional qualifier for the child segment (may be null)
   * @return the child Path corresponding to this / (seqment, seqmentQ)
   */
  def path(seqment: String, seqmentQ: String): Path;

  /**
   * A locality-aware view of this Path. Semantics are implementation-defined
   * (for example, a path that represents a remote resource may return a local
   * equivalent). Implementations should document the exact behavior.
   */
  def local: Path

  /**
   * The printable name of this node. Combines `segment` and `segmentQ` when
   * the qualifier is present.
   */
  def localName: String | String = this.segment + (if (this.segmentQ != null) this.segmentQ else "")

  /**
   * Convert this Path to a tuple of (segment, segmentQ, parent) for pattern
   * matching or simple destructuring.
   */
  def toTuple: (String, String, Path) = (this.segment, this.segmentQ, this.parent)

  /** Convenience alias for `path(seqment)`. */
  def +(seqment: String): Path = this.path(seqment)

  /** Convenience alias for `path(x._1, x._2)`. */
  def +(x: (String, String)): Path = this.path(x._1, x._2)

  /** Convenience alias for `path(seqment)`. */
  def /(seqment: String): Path = this.path(seqment)

  /** Convenience alias for `path(x._1, x._2)`. */
  def /(x: (String, String)): Path = this.path(x._1, x._2)

  /**
   * Expand this path into a list of path nodes, typically from the root down
   * to this node. The exact order should be documented by implementations but
   * generally contains the ancestor chain including this node.
   */
  def expand: List[Path]

  /** Number of segments from the root to this node (inclusive). */
  def length: Int

  /**
   * Returns true when this path is a strict or non-strict parent of `path`.
   * Implementations should specify whether equality counts as parent/ancestor.
   *
   * @param path the path to test against
   */
  def isParentOf(path: Path): Boolean

  /**
   * Compute a path relative to this path. Equivalent to the filesystem
   * concept of relativize: for base `b` and target `t`, `b.relativize(t)`
   * yields the relative path from `b` to `t`.
   *
   * @param path the target path to relativize against this one
   * @return a Path representing `path` relative to this instance
   */
  def relativize(path: Path): Path
}

/**
 * Companion object for Path providing factory methods, constants and
 * convenient conversions.
 */
object Path {
  /** Segment delimiter used when parsing string representations. */
  val DELIMITER = '/'

  /** Delimiter used to separate a segment and its qualifier when encoded. */
  val SEGMENT_SEGMENTQ_DELIMITER = ":"

  /**
   * Create a Path from a single segment string. The segment string may contain
   * delimiters; it will be split using [[DELIMITER]] and folded into a
   * hierarchical Path.
   *
   * @param segment a possibly-delimited segment string
   */
  inline def apply(segment: String): Path = {
    this.apply(segment, null)
  }

  /** Alias for [[apply]] that improves readability: Path.of("a/b") */
  def of(segment: String): Path = this.apply(segment)

  /**
   * Create a Path from a segment string and an optional segment qualifier.
   * The string is split on [[DELIMITER]] and each non-empty piece becomes a
   * segment in the resulting Path hierarchy. The provided `segmentQ` is used
   * for the first segment encountered while splitting.
   *
   * @param segment the (possibly-delimited) segment string
   * @param segmentQ optional qualifier applied to the first segment
   */
  inline def apply(segment: String, segmentQ: String | Null): Path = {
    segment.split(Path.DELIMITER)
      .filter(e => e != null && !"".equals(e))
      .foldLeft[Path](null)((left, right) => if (left == null) EPathFactory(right, segmentQ, null) else left.path(right))
  }

  /** Alias for [[apply(segment, segmentQ)]]. */
  def of(segment: String, segmentQ: String | Null): Path = this.apply(segment, segmentQ)

  /**
   * Extractor for pattern matching a Path into (segment, segmentQ, parent).
   * Example: `case Path(segment, q, parent) => ...`.
   */
  def unapply(x: Path): (String, String, Path) = {
    (x.segment, x.segmentQ, x.parent)
  }

  /** Implicit conversion from String to Path using [[Path.apply]]. */
  given Conversion[String, Path] with {
    override def apply(segment: String): Path = Path(segment)
  }

  /** Implicit conversion from (String,String) tuple to Path. */
  given Conversion[(String, String), Path] with {
    override def apply(x: (String, String)): Path = Path(x._1, x._2)
  }

  /** Implicit conversion from Path to a (segment, segmentQ, parent) tuple. */
  given Conversion[Path, (String, String, Path)] with {
    override def apply(path: Path): (String, String, Path) = {
      (path.segment, path.segmentQ, path.parent)
    }
  }
}