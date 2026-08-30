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
package io.github.mathter.morph.data

import io.github.mathter.morph.path.Path

import java.io.{ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable

private case class InnerMap(val id: Int)
  extends mutable.LinkedHashMap[Path, mutable.Queue[Any]]
    with Serializable {
  protected override def writeReplace(): AnyRef = new Serialization(this)
}

@SerialVersionUID(1L)
private[data] class Serialization(@transient var map: InnerMap)
  extends Serializable {
  private def writeObject(out: ObjectOutputStream): Unit = {
    out.writeInt(map.id)
    out.writeInt(map.size)

    this.map
      .foreach {
        x =>
          out.writeObject(x)
      }
  }

  private def readObject(in: ObjectInputStream): Unit = {
    val id = in.readInt()
    var k = in.readInt()

    if (k >= 0) {
      this.map = new InnerMap(id)
      this.map.sizeHint(k)

      while (k > 0) {
        this.map.addOne(in.readObject().asInstanceOf[(Path, mutable.Queue[Any])])
        k -= 1
      }
    }
  }

  protected def readResolve(): Any = this.map
}

private[data] object InnerMap {
  def newQueue: mutable.Queue[Any] = mutable.Queue.empty
}