package io.github.mathter.morph.processing.xml.simple

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.path.Path
import io.github.mathter.morph.processing.xml.simple.ParserFactory
import org.junit.jupiter.api.{Assertions, Test}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, OutputStreamWriter, Writer}
import scala.xml.{InputSource, XML}

class SaxParserTest {
  @Test
  def test(): Unit = {
    val xml = {
      <document>
        <title>My document</title>
        <pageCount>2</pageCount>
        <pages>
          <page>
            <number>1</number>
            <content>Page 1 content</content>
          </page>
          <page>
            <number>2</number>
            <content>Page 2 content</content>
          </page>
        </pages>
      </document>
    }

    val baos = new ByteArrayOutputStream
    val w: Writer = new OutputStreamWriter(baos)

    XML.write(w, xml, "UTF-8", true, null)
    w.close()

    val is = new InputSource(new ByteArrayInputStream(baos.toByteArray))
    val parser = ParserFactory.newNSInstance().xmlParser
    val pathMap = parser.parse(is)

    Assertions.assertNotNull(pathMap)

    val documentPathMap = pathMap("document")
    Assertions.assertNotNull(documentPathMap)
    Assertions.assertTrue(documentPathMap.isDefined)

    val title = pathMap("document/title")
    Assertions.assertNotNull(title)
    Assertions.assertTrue(title.isDefined)
    Assertions.assertEquals("My document", title.get)

    val pageCount = pathMap("document/pageCount")
    Assertions.assertNotNull(pageCount)
    Assertions.assertTrue(pageCount.isDefined)
    Assertions.assertEquals("2", pageCount.get)

    val pages = pathMap[List[PathMap]]("document/pages/page")
    Assertions.assertNotNull(pages)
    Assertions.assertTrue(pages.isDefined)
    Assertions.assertEquals(2, pages.get.length)
    Assertions.assertNotEquals("1", pages.map(_(0).get[String]("number").get))
    Assertions.assertNotEquals("Page 2 content", pages.map(_(0).get[String]("content").get))
    Assertions.assertNotEquals("1", pages.map(_(1).get[String]("number").get))
    Assertions.assertNotEquals("Page 2 content", pages.map(_(1).get[String]("content").get))
  }
}

