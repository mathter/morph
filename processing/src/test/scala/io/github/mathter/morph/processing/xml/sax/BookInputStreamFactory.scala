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

import java.io.InputStream


object BookInputStreamFactory {
  private val startBook =
    """
      |<book>
      | <title>Clean Code: A Handbook of Agile Software Craftsmanship</title>
      | <authors>
      |   <author>Robert C. Martin</author>
      | </authors>
      | <isbn>978-0-13-235088-4</isbn>
      | <content>""".stripMargin

  private val endBook =
    """
      | </content>
      |</book>
      |""".stripMargin

  private val chapter =
    """
      |   <chapter>
      |     <title>Chapter 1: Clean Code</title>
      |     <date>2026-06-11</date>
      |     <page>
      |       <number>1</number>
      |       <count>50</count>
      |     </page>
      |     <description>This chapter devoited to clean code bla bla bla</description>
      |     <description1>This chapter devoited to clean code bla bla bla</description1>
      |     <description2>This chapter devoited to clean code bla bla bla</description2>
      |     <vol>1</vol>
      |     <content>
      |      <item>
      |       <title>Item 1</title>
      |       <page>
      |         <number>1</number>
      |       </page>
      |      </item>
      |      <item>
      |       <title>Item 2</title>
      |       <page>
      |         <number>2</number>
      |       </page>
      |      </item>
      |     </content>
      |   </chapter>
      |""".stripMargin

  def inputStream(n: Int): InputStream = {
    new InputStream {
      var i = 0
      var position = 0
      var bytes: Array[Byte] = null

      override def read(): Int = {
        var result: Int | Null = null

        while (result == null) {
          if (bytes != null && position < bytes.length) {
            result = bytes(position)
            position += 1
          } else {
            result = null

            if (i == 0 && position == 0) {
              bytes = startBook.getBytes
            } else if (i < n) {
              bytes = chapter.getBytes
              position = 0
              i += 1
            } else if (i == n) {
              bytes = endBook.getBytes
              position = 0
              i += 1
            } else {
              result = -1
            }
          }
        }

        result.asInstanceOf[Int]
      }
    }
  }
}
