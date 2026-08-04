package io.github.mathter.morph.processing.ext.xml.sax

import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.processing.ext.AbstractListener
import io.github.mathter.morph.processing.json.JsonSerializer

import java.io.InputStream
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import scala.util.Using

object LoadTest {
  def main(args: Array[String]): Unit = {
    val serializer = JsonSerializer()
    val t = new SaxXmlTransformer(SAXParserFactory.newNSInstance())
    val listener = new AbstractListener(
      "book/content/chapter",
      Set("book/authors", "book/isbn")
    ) {
      override def apply(pathMap: PathMap): Unit = {
        // Do nothing
      }
    }
    t.addListener(listener)

    val n = 1_000_000
    Using(BookInputStreamFactory.inputStream(n)) {
      is =>
        val source = StreamSource(is, "book.xml")

        val start = System.nanoTime()
        t.resolve(source)
        println((System.nanoTime() - start) / 1_000_000_000.0 / n)
    }.recover(e => e.printStackTrace())
  }
}
