package io.github.mathter.morph.path

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.{Assertions, Test}
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{Arguments, ArgumentsProvider, ArgumentsSource}
import org.junit.jupiter.params.support.ParameterDeclarations

import java.util.stream

class PathTest extends ArgumentsProvider {
  @Test
  def testSegment(): Unit = {
    val path: Path = "segment";
    Assertions.assertNotNull(path)

    val (segment, segmentQ, parent) = path.toTuple
    Assertions.assertEquals("segment", segment)
    Assertions.assertNull(segmentQ)
    Assertions.assertNull(parent)
  }

  @Test
  def testSegmentSeqmentQ(): Unit = {
    val path: Path = ("segment", "segmentQ");
    Assertions.assertNotNull(path)

    val (segment, segmentQ, parent) = path.toTuple
    Assertions.assertEquals("segment", segment)
    Assertions.assertEquals("segmentQ", segmentQ)
    Assertions.assertNull(parent)
  }

  @Test
  def testParent(): Unit = {
    val parentPath: Path = ("segment", "segmentQ")
    val path: Path = parentPath.path("segment1", "segmentQ1")

    Assertions.assertNotNull(path)
    val (segment, segmentQ, parent) = path.toTuple
    Assertions.assertEquals("segment1", segment)
    Assertions.assertEquals("segmentQ1", segmentQ)
    Assertions.assertEquals(parentPath, parent)
  }

  @Test
  def testAddSeqment(): Unit = {
    val parentPath: Path = ("segment", "segmentQ")
    val path: Path = parentPath + "segment1"

    Assertions.assertNotNull(path)
    val (segment, segmentQ, parent) = path.toTuple
    Assertions.assertEquals("segment1", segment)
    Assertions.assertEquals(null, segmentQ)
    Assertions.assertEquals(parentPath, parent)
  }

  @Test
  def testAddSeqmentSeqmentQ(): Unit = {
    val parentPath: Path = ("segment", "segmentQ")
    val path: Path = parentPath + ("segment1", "segmentQ1")

    Assertions.assertNotNull(path)
    val (segment, segmentQ, parent) = path.toTuple
    Assertions.assertEquals("segment1", segment)
    Assertions.assertEquals("segmentQ1", segmentQ)
    Assertions.assertEquals(parentPath, parent)
  }

  @Test
  def testExtend(): Unit = {
    val path0: Path = ("segment", "segmentQ")
    val path1: Path = path0 + ("seqment1", "segmentQ1")

    val expanded = path1.expand
    Assertions.assertNotNull(expanded)
    Assertions.assertEquals(2, expanded.length)
    Assertions.assertEquals(path0, expanded(0))
    Assertions.assertEquals(path1, expanded(1))
  }

  @Test
  def testEquals(): Unit = {
    val p0: Path = ("seqment", "q")
    val p1: Path = ("seqment", "q")

    Assertions.assertTrue(p0.equals(p1))
    Assertions.assertTrue(p0 eq p1)
  }

  @Test
  def testEquals2(): Unit = {
    val p: Path = "parent"
    val p0: Path = p / ("seqment", "q")
    val p1: Path = p / ("seqment", "q")

    Assertions.assertTrue(p0.equals(p1))
    Assertions.assertTrue(p0 eq p1)
  }

  @Test
  def testSeqments(): Unit = {
    val path: Path = ("/segment0/segment1/segment2", "segmentQ0")

    val expanded = path.expand
    Assertions.assertNotNull(path)
    Assertions.assertEquals(3, expanded.length)

    var result = expanded(0)
    Assertions.assertEquals("segment0", result.segment)
    Assertions.assertEquals("segmentQ0", result.segmentQ)
    Assertions.assertNull(result.parent)

    result = expanded(1)
    Assertions.assertEquals("segment1", result.segment)
    Assertions.assertEquals(null, result.segmentQ)
    Assertions.assertEquals(expanded(0), result.parent)

    result = expanded(2)
    Assertions.assertEquals("segment2", result.segment)
    Assertions.assertEquals(null, result.segmentQ)
    Assertions.assertEquals(expanded(1), result.parent)
  }

  @Test
  def testLocal(): Unit = {
    val path: Path = ("/segment0/segment1/segment2", "segmentQ0")
    val (segment, segmentQ, parent) = path.local.toTuple

    Assertions.assertEquals("segment2", segment)
    Assertions.assertNull(segmentQ)
    Assertions.assertNull(parent)
  }

  @Test
  def testLenght(): Unit = {
    val path: Path = ("/segment0/segment1/segment2")
    Assertions.assertNotNull(path)
    Assertions.assertEquals(3, path.length)
  }

  @Test
  def testIsParentOf(): Unit = {
    val path0: Path = ("/segment0/segment1/segment2")
    val path1: Path = ("/segment0/segment1")
    Assertions.assertTrue(path1.isParentOf(path0))
  }

  @ArgumentsSource(classOf[PathTest])
  @ParameterizedTest
  def testRelativize(left: Path, right: Path, result: Path): Unit = {
    val rel = left.relativize(right)
    Assertions.assertEquals(result, rel)
  }

  override def provideArguments(parameters: ParameterDeclarations, context: ExtensionContext): stream.Stream[_ <: Arguments] = {
    stream.Stream.of(
      Arguments.of(
        Path("/segment0/segment1"),
        Path("/segment0/segment1/segment2/segmnt3"),
        Path("/segment2/segmnt3")
      ),
      Arguments.of(
        Path("segment0"),
        Path("segment1"),
        null
      ),
      Arguments.of(
        Path("segment0/segment1"),
        Path("segment0"),
        null
      )
    )
  }
}