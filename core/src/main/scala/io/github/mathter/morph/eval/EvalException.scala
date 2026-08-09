package io.github.mathter.morph.eval

class EvalException(val tracer: Tracer, message: String, throwable: Throwable)
  extends RuntimeException(message, throwable) {

  def this(tracer: Tracer, throwable: Throwable) = {
    this(tracer, null, throwable)
  }

  override def getMessage: String = {
    super.getMessage + '\n' + this.tracer.toString
  }
}
