// Copyright (c) 2026 mathter
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, without
// warranties or condition of any kind. You may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// All rights reserved.
package io.github.mathter.morph.processing.xml.sax

import io.github.mathter.morph.conv.DateTimeConv
import io.github.mathter.morph.data.PathMap
import io.github.mathter.morph.dsl.base.{BaseContext, BaseDsl, Evaluator}
import io.github.mathter.morph.eval.Terminal
import io.github.mathter.morph.processing.json.JsonSerializer
import io.github.mathter.morph.processing.xml.sax.{AbstractListener, SaxXmlTransformer}

import java.time.LocalDate
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import scala.util.Using

object LoadAndTransformTest {
  def main(args: Array[String]): Unit = {
    val serializer = JsonSerializer()
    val t = new SaxXmlTransformer(SAXParserFactory.newNSInstance())
    val terms = terminals()
    val listener = new AbstractListener(
      "book/content/chapter",
      Set("book/authors", "book/isbn")
    ) {
      override def apply(pathMap: PathMap): Unit = {
        implicit val context = new BaseContext(pathMap)
        terms.foreach(e => Evaluator.eval(e))
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

  def terminals(): Set[Terminal] = {
    import io.github.mathter.morph.dsl.given
    implicit val dsl = BaseDsl()

    val result = dsl.obj

    Set(
      result.by("chapter/title").from(dsl.origin.by("chapter/title")),
      result.by("chapter/date").from(
        dsl.origin.by("chapter/date")
          .map(DateTimeConv.string2localDate)
      ),
      result.by("chapter/pageInfo/pageNumber").from(dsl.origin.by("chapter/page/number")),
      result.by("chapter/pageInfo/pageCount").from(dsl.origin.by("chapter/page/count")),
      result.by("chapter/val").from(dsl.origin.by("chapter/vol")),
      result.by("chapter/content/items").from(
        dsl.origin
          .by("chapter/content/item/title")
          .as[List[String]]
          .mapsElem(e => e.toLowerCase)
      ),
      dsl.result.from(result)
    )
  }
}
