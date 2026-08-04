package io.github.mathter.morph.path

trait Path {
  def segment: String;

  def segmentQ: String;

  def parent: Path;

  def path(seqment: String): Path;

  def path(seqment: String, seqmentQ: String): Path;

  def local: Path

  def localName: String | String = this.segment + (if (this.segmentQ != null) this.segmentQ else "")

  def toTuple: (String, String, Path) = (this.segment, this.segmentQ, this.parent)

  def +(seqment: String): Path = this.path(seqment)

  def +(x: (String, String)): Path = this.path(x._1, x._2)

  def /(seqment: String): Path = this.path(seqment)

  def /(x: (String, String)): Path = this.path(x._1, x._2)

  def expand: List[Path]

  def length: Int

  def isParentOf(path: Path): Boolean

  def relativize(path: Path): Path
}

object Path {
  val DELIMITER = '/'

  val SEGMENT_SEGMENTQ_DELIMITER = ":"

  def apply(segment: String): Path = {
    this.apply(segment, null)
  }

  def apply(segment: String, segmentQ: String | Null): Path = {
    segment.split(Path.DELIMITER)
      .filter(e => e != null && !"".equals(e))
      .foldLeft[Path](null)((left, right) => if (left == null) EPathFactory.of(right, segmentQ, null) else left.path(right))
  }

  def unapply(x: Path): (String, String, Path) = {
    (x.segment, x.segmentQ, x.parent)
  }

  given Conversion[String, Path] with {
    override def apply(segment: String): Path = Path(segment)
  }

  given Conversion[(String, String), Path] with {
    override def apply(x: (String, String)): Path = Path(x._1, x._2)
  }

  given Conversion[Path, (String, String, Path)] with {
    override def apply(path: Path): (String, String, Path) = {
      (path.segment, path.segmentQ, path.parent)
    }
  }
}