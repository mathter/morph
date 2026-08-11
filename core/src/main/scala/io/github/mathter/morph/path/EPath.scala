package io.github.mathter.morph.path

import java.io.{ObjectInputStream, ObjectOutputStream}

private case class EPath(val segment: String, val segmentQ: String, val parent: Path, val hc: Int) extends Path {
  private var expandedCache: List[Path] = null;

  private var localCache: Path = null

  override def path(seqment: String): Path = EPathFactory.of(seqment, null, this)

  override def path(seqment: String, seqmentQ: String): Path = EPathFactory.of(seqment, seqmentQ, this)

  override def local: Path = {
    if (this.localCache == null) {
      this.localCache = EPathFactory.of(this.segment, this.segmentQ, null)
    }

    this.localCache
  }

  override def expand: List[Path] = {
    if (this.expandedCache == null) {
      this.expandedCache = if (this.parent != null) this.parent.expand :+ this else List(this)
    }

    this.expandedCache
  }

  override def length: Int = this.expand.length

  override def isParentOf(path: Path): Boolean = {
    val expanded = path.expand

    if (this.length <= expanded.length) {
      this equals expanded(this.length - 1)
    } else {
      false
    }
  }

  override def relativize(path: Path): Path = {
    val expanded = path.expand

    if (this.length <= expanded.length && (this equals expanded(this.length - 1))) {
      expanded.drop(this.length)
        .fold(null)((left, right) => {
          if (left == null) right.local else left.local + (right.segment, right.segmentQ)
        })
    } else {
      null
    }
  }

  override def hashCode(): Int = this.hc

  override def equals(obj: Any): Boolean = this.asInstanceOf[AnyRef] eq obj.asInstanceOf[AnyRef]

  override def canEqual(that: Any): Boolean = that.isInstanceOf[EPath]

  override def toString: String = {
    if (parent != null) {
      this.parent.toString + Path.DELIMITER + this.localName
    } else {
      this.localName
    }
  }

  protected def writeReplace(): AnyRef = new Serializer(this.segment, this.segmentQ, this.parent.asInstanceOf[EPath])
}

private class Serializer(@transient var segment: String, @transient var segmentQ: String, @transient var parent: EPath) extends Serializable {
  private def writeObject(out: ObjectOutputStream): Unit = {
    out.writeObject(this.segment)
    out.writeObject(this.segmentQ)

    if (this.parent != null) {
      out.writeBoolean(true)
      out.writeObject(this.parent)
    } else {
      out.writeBoolean(false)
    }
  }

  private def readObject(in: ObjectInputStream): Unit = {
    this.segment = in.readObject().asInstanceOf[String]
    this.segmentQ = in.readObject().asInstanceOf[String]

    if (in.readBoolean()) {
      this.parent = in.readObject().asInstanceOf[EPath]
    } else {
      this.parent = null
    }
  }

  protected def readResolve(): Any = if (this.parent != null) this.parent.path(this.segment, this.segmentQ) else Path(this.segment, this.segmentQ)
}