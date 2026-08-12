package io.github.mathter.morph.data

import org.junit.jupiter.api.Assertions.{assertEquals, assertSame, assertThrows, assertTrue}
import org.junit.jupiter.api.Test

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.util

class ImmutablePathMapTest {
  private def populatedSource(): PathMap = {
    val pm = PathMap.empty
    pm("title") = "My Book"
    pm("authors/name") = "John Doe"
    pm("authors/lastName") = "Doe"
    pm("authors/name") = "Jane Roe"
    pm("year") = 2024
    pm
  }

  @Test
  def testReadsMatchSource(): Unit = {
    val source = populatedSource()
    val immutable = source.asImmutable

    assertEquals(source("title"), immutable("title"))
    assertEquals(source("authors/lastName"), immutable("authors/lastName"))
    assertEquals(source("year"), immutable("year"))
    assertEquals(source.get("title"), immutable.get("title"))
    assertEquals(source.keys.map(_.segment), immutable.keys.map(_.segment))
    assertEquals(source.entries.map(_._1.segment), immutable.entries.map(_._1.segment))
    assertEquals(source[List[?]]("authors/name").get, immutable[List[?]]("authors/name").get)

    val nested: PathMap = immutable("authors").get
    assertEquals(Opt("Doe"), nested("lastName"))

    assertEquals("My Book", immutable.toMap(_.segment)("title"))
    val javaMap = immutable.toJavaMap(_.segment).get("authors").asInstanceOf[util.Map[String, Object]]
    assertEquals("Doe", javaMap.get("lastName"))

    val iget = immutable.iget[List[String]]("authors")
    assertTrue(iget.isDefined)
    assertEquals(3, iget.get.length)
  }

  @Test
  def testUpdateThrows(): Unit = {
    val immutable = populatedSource().asImmutable

    val exception = assertThrows(classOf[UnsupportedOperationException], () => immutable("new") = "value")
    assertEquals("ImmutablePathMap does not support update operation", exception.getMessage)
    assertThrows(classOf[UnsupportedOperationException], () => immutable.put("new", "value"))
  }

  @Test
  def testNestedReadsAreImmutable(): Unit = {
    val immutable = populatedSource().asImmutable

    val authors: PathMap = immutable("authors").get
    assertTrue(authors.isInstanceOf[ImmutablePathMap])
    assertThrows(classOf[UnsupportedOperationException], () => authors.asInstanceOf[PathMap]("name") = "x")

    val entriesAuthor = immutable.entries.collectFirst { case (path, value: PathMap) if path.segment == "authors" => value }.get
    assertTrue(entriesAuthor.isInstanceOf[ImmutablePathMap])
    assertThrows(classOf[UnsupportedOperationException], () => entriesAuthor("name") = "x")
  }

  @Test
  def testListElementsAreImmutable(): Unit = {
    val source = PathMap.empty
    val author0 = PathMap.empty
    author0("name") = "John Doe"
    val author1 = PathMap.empty
    author1("name") = "Jane Roe"
    source("authors") = List(author0, author1)

    val immutable = source.asImmutable
    val list = immutable[List[?]]("authors").get
    assertEquals(2, list.length)
    assertTrue(list.forall(_.isInstanceOf[ImmutablePathMap]))
    list.foreach(author => assertThrows(classOf[UnsupportedOperationException], () => author.asInstanceOf[PathMap]("name") = "x"))
  }

  @Test
  def testFromIdempotent(): Unit = {
    val immutable = populatedSource().asImmutable
    assertSame(immutable, ImmutablePathMap.from(immutable))
  }

  @Test
  def testEmpty(): Unit = {
    val empty = ImmutablePathMap.empty
    assertTrue(empty.keys.isEmpty)
    assertTrue(empty.entries.isEmpty)
    assertThrows(classOf[UnsupportedOperationException], () => empty("key") = "value")
  }

  @Test
  def testViewsAreReadOnly(): Unit = {
    val immutable = populatedSource().asImmutable

    val asJava = immutable.asJava
    assertTrue(asJava.isInstanceOf[ImmutablePathMap])
    assertThrows(classOf[UnsupportedOperationException], () => asJava("new") = "value")

    val asScala = asJava.asScala
    assertTrue(asScala.isInstanceOf[ImmutablePathMap])
    assertThrows(classOf[UnsupportedOperationException], () => asScala("new") = "value")
    assertEquals(Opt("Doe"), asScala("authors/lastName"))
  }

  @Test
  def testJPathMapAsImmutable(): Unit = {
    val jpm = PathMap.jempty
    jpm("key") = "value"

    val immutable = jpm.asImmutable
    assertTrue(immutable.isInstanceOf[ImmutableJPathMap])
    assertEquals(Opt("value"), immutable("key"))
    assertThrows(classOf[UnsupportedOperationException], () => immutable("new") = "value")
  }

  @Test
  def testImmutableJPathMapFrom(): Unit = {
    val jpm = PathMap.jempty
    jpm("key") = "value"

    val immutable = ImmutableJPathMap.from(jpm)
    assertTrue(immutable.isInstanceOf[ImmutableJPathMap])
    assertEquals(Opt("value"), immutable("key"))
    assertThrows(classOf[UnsupportedOperationException], () => immutable("new") = "value")
    assertSame(immutable, ImmutableJPathMap.from(immutable))

    val jempty = ImmutableJPathMap.empty
    assertTrue(jempty.keys.isEmpty)
    assertThrows(classOf[UnsupportedOperationException], () => jempty("new") = "value")
  }

  @Test
  def testBackedViewVisibility(): Unit = {
    val source = PathMap.empty
    source("key") = "value"

    val immutable = source.asImmutable
    assertEquals(Opt("value"), immutable("key"))

    source("other") = "changed"
    assertEquals(Opt("changed"), immutable("other"))
  }

  @Test
  def testSerializable(): Unit = {
    val immutable = populatedSource().asImmutable

    val baos = new ByteArrayOutputStream()
    val os = new ObjectOutputStream(baos)
    os.writeObject(immutable)
    os.close()

    val io = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray))
    val result = io.readObject().asInstanceOf[PathMap]
    assertEquals(Opt("My Book"), result("title"))
    assertThrows(classOf[UnsupportedOperationException], () => result("new") = "value")
  }
}