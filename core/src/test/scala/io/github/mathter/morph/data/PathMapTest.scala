package io.github.mathter.morph.data

import io.github.mathter.morph.data.PathMapTest.randomStrings
import io.github.mathter.morph.path.Path
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.{Assertions, Test}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

class PathMapTest {
  @Test
  def testGet(): Unit = {
    val title: Path = "title"
    val titleValue = randomStrings.nextGraph(10)

    val authors: Path = "authors"
    val authorName: Path = "a/name"
    val authorsName: Path = "authors/a/name"
    val authorLastName: Path = "a/lastName"
    val authorsLastName = "authors/a/lastName"

    val authorName0 = randomStrings.nextAlphabetic(10)
    val authorLastName0 = randomStrings.nextAlphabetic(10)

    val authorName1 = randomStrings.nextAlphabetic(10)
    val authorLastName1 = randomStrings.nextAlphabetic(10)

    val isbn: Path = "isbn"
    val isbnValue = randomStrings.nextAlphabetic(10)

    val year: Path = "year"
    val yearValue = 2024

    val bookPathMap = PathMap.empty
    bookPathMap(title) = titleValue

    val author0PathMap = PathMap.empty
    author0PathMap(authorName) = authorName0
    author0PathMap(authorLastName) = authorLastName0
    bookPathMap(authors) = author0PathMap

    val author1PathMap = PathMap.empty
    author1PathMap(authorName) = authorName1
    author1PathMap(authorLastName) = authorLastName1
    bookPathMap(authors) = author1PathMap

    bookPathMap(isbn) = isbnValue
    bookPathMap(year) = yearValue

    Assertions.assertEquals(Opt(titleValue), bookPathMap(title))

    val authorsResultPathList = bookPathMap[List[?]](authors).get
    Assertions.assertNotNull(authorsResultPathList)
    Assertions.assertTrue(authorsResultPathList.isInstanceOf[List[?]])

    val authorResult0: PathMap = authorsResultPathList(0).asInstanceOf[PathMap]
    Assertions.assertNotNull(authorResult0)
    Assertions.assertEquals(Opt(authorName0), authorResult0(authorName))
    Assertions.assertEquals(Opt(authorLastName0), authorResult0(authorLastName))

    val authorResult1: PathMap = authorsResultPathList(1).asInstanceOf[PathMap]
    Assertions.assertNotNull(authorResult1)
    Assertions.assertEquals(Opt(authorName1), authorResult1(authorName))
    Assertions.assertEquals(Opt(authorLastName1), authorResult1(authorLastName))

    Assertions.assertEquals(Opt(isbnValue), bookPathMap(isbn))
    Assertions.assertEquals(Opt(yearValue), bookPathMap(year))
  }

  @Test
  def igetTest(): Unit = {
    val pm00 = PathMap.empty
    pm00("v0") = "v00"
    pm00("v1") = "v01"
    pm00("v2") = "v02"
    pm00("v3") = "v03"
    pm00("v4/v0") = "v040"

    val pm01 = PathMap.empty
    pm01("v0") = "v10"
    pm01("v1") = "v11"
    pm01("v2") = "v12"
    pm01("v3") = "v13"
    pm01("v4/v0") = "v140"

    val pm = PathMap.empty
    pm("p0/p1/pm0") = pm00
    pm("p0/p1/pm1") = pm01
    pm("p0/p1/pm0") = "Some text"
    pm("p0/p1/pm2") = "Some text2"
    pm("p0/p1") = "Some text3"
    pm("p0/p1") = "Some text4"

    val list = pm.iget[List[String]]("p0/p1")
    Assertions.assertNotNull(list)
    Assertions.assertTrue(list.isDefined)
    Assertions.assertEquals(4, list.get.length)
  }

  @Test
  def testEmpty(): Unit = {
    val pm = PathMap.empty

    Assertions.assertTrue(pm.isEmpty)
    Assertions.assertFalse(pm.nonEmpty)

    pm("p0") = "p0"
    Assertions.assertFalse(pm.isEmpty)
    Assertions.assertTrue(pm.nonEmpty)
  }

  @Test
  def testKeys(): Unit = {
    val pm = PathMap.empty

    pm("p0/p01") = "p01"
    pm("p0/p02") = "p02"
    pm("p1") = "p1"
    pm("p2") = "p2"

    val keys = pm.keys
    Assertions.assertNotNull(keys)
    Assertions.assertEquals(3, keys.size)
    Assertions.assertEquals(Set("p0", "p1", "p2").toString(), keys.toString())
  }

  @Test
  def testEntries(): Unit = {
    val pm = PathMap.empty

    pm("p0/p01") = "p01"
    pm("p0/p02") = "p02"
    pm("p1") = "p1"
    pm("p1") = "p1.2"
    pm("p2") = "p2"

    val entries = pm.entries
    Assertions.assertNotNull(entries)
    Assertions.assertEquals(3, entries.size)
    Assertions.assertEquals(List("p0", "p1", "p2").toString(), entries.map(_._1).toString())
    Assertions.assertEquals("p01", entries(0)._2.asInstanceOf[PathMap]("p01").get)
    Assertions.assertEquals("p02", entries(0)._2.asInstanceOf[PathMap]("p02").get)
    Assertions.assertEquals(List("p1", "p1.2").fold("")(_ + _), entries(1)._2.asInstanceOf[List[String]].fold("")(_ + _))
    Assertions.assertEquals("p2", entries(2)._2)
  }

  @Test
  def testSerializable(): Unit = {
    val pm = PathMap.empty
    pm("p0/p1") = 10

    val baos = new ByteArrayOutputStream()
    val os = new ObjectOutputStream(baos)

    os.writeObject(pm)
    os.close()

    val io = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray))

    val result = io.readObject().asInstanceOf[PathMap]
    Assertions.assertNotNull(result)
    Assertions.assertEquals(10, result("p0/p1").get.asInstanceOf[Int])
  }
}

object PathMapTest {
  val randomStrings = RandomStringUtils.insecure()
}
