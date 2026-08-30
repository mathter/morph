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
package io.github.mathter.morph.dsl.base

import io.github.mathter.morph.data.{Opt, PathMap}
import io.github.mathter.morph.eval.Context

import scala.collection.mutable

class BaseContext(val origin: PathMap,
                  val results: mutable.Map[Any, Opt[Any]] = mutable.HashMap.empty,
                  val cache: mutable.Map[Any, Opt[Any]] = mutable.HashMap.empty
                 ) extends Context {

  def this(origin: PathMap) = {
    this(origin, mutable.HashMap.empty, mutable.HashMap.empty)
  }

  override def target[T](tag: Any): Opt[T] =
    this.results.getOrElseUpdate(tag, Opt.empty[T]).asInstanceOf[Opt[T]]


  override def target[T](tag: Any, opt: Opt[T]): Opt[T] = {
    this.results.put(tag, opt)
    opt
  }
}
