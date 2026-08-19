package io.github.mathter.morph.data

import org.junit.jupiter.api.{Assertions, Test}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInput, ObjectInputStream, ObjectOutputStream}
import scala.util.Using

class InnerMapTest {
  @Test
  def testSerialization(): Unit = {
    val origin: InnerMap = new InnerMap(11)

    origin("path") = InnerMap.newQueue.appended("Hi")

    val readed = Using(new ByteArrayOutputStream()) {
      baos => {
        Using(new ObjectOutputStream(baos)) {
          oo =>
            oo.writeObject(origin)
            baos
        }
          .get
      }
    }
      .map(_.toByteArray)
      .map(new ByteArrayInputStream(_))
      .map(new ObjectInputStream(_))
      .map(oi => oi.readObject())
      .get

    Assertions.assertTrue(readed.isInstanceOf[InnerMap])

    val map = readed.asInstanceOf[InnerMap]
    Assertions.assertEquals(origin.id, map.id)
    Assertions.assertEquals(origin, map)
  }
}
