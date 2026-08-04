package io.github.mathter.morph.eval

import scala.collection.mutable

class Tracer(private val parent: Tracer,
             private val stackTraceElement: StackTraceElement,
             private val level: Int
            ) {
  override def toString: String = {
    val sb = new StringBuilder()
    sb.append("\tlevel: ").append(this.level).append('\n')
    sb.append("\tstacktrace: ").append(this.stackTraceElement).append('\n')

    if (this.parent != null) {
      sb.append("\tprev: ").append('\n').append(this.parent).append('\n')
    }

    sb.toString()
  }
}

object Tracer {
  private val local = new ThreadLocal[mutable.Stack[Tracer]]

  local.set(mutable.Stack())

  def trace3(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(3), stackTrace.length)
    stack.push(tracer)
    tracer
  }

  def trace4(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(4), stackTrace.length)
    stack.push(tracer)
    tracer
  }

  def trace5(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(5), stackTrace.length)
    stack.push(tracer)
    tracer
  }

  def trace6(): Tracer = {
    val stackTrace = Thread.currentThread().getStackTrace
    val stack = local.get()
    var prev = if (stack.isEmpty) null else stack.top

    while (prev != null && prev.level >= stackTrace.length) {
      stack.pop()
      prev = if (stack.isEmpty) null else stack.top
    }

    val tracer = new Tracer(prev, stackTrace(6), stackTrace.length)
    stack.push(tracer)
    tracer
  }
}
