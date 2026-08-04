package io.github.mathter.jmorph.dsl.base

import JavaNumeric.*
import io.github.mathter.jmorph.dsl.{BooleanSource, Dsl, ListSource, NumberSource, StringSource}
import io.github.mathter.morph.data.Opt
import io.github.mathter.morph.dsl.base.eval.AbstractEval
import io.github.mathter.morph.dsl.base.given
import io.github.mathter.morph.dsl.{Source, Dsl as zDsl}
import io.github.mathter.morph.eval.{Context, Eval, Tracer}

import java.math.{BigDecimal, BigInteger}
import java.util.function.Supplier
import java.{lang, util}
import scala.jdk.CollectionConverters

class BaseDsl extends io.github.mathter.morph.dsl.base.BaseDsl with Dsl {
  override def asStringSource(source: Source[String]): StringSource = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    source match {
      case x: StringSource => x
      case x: Source[String] => new StringSourceEval(null) {
        override def evalI(using context: Context): Opt[String] = source.asInstanceOf[Eval[String]].eval
      }
    }
  }

  override def asByteSource(source: Source[lang.Byte]): NumberSource[lang.Byte] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Byte] = Numeric[lang.Byte]

    source match {
      case x: NumberSource[lang.Byte] => x
      case x: Source[lang.Byte] => new NumberSourceEval[lang.Byte](source)
    }
  }

  override def asShortSource(source: Source[lang.Short]): NumberSource[lang.Short] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Short] = Numeric[lang.Short]

    source match {
      case x: NumberSource[lang.Short] => x
      case x: Source[lang.Short] => new NumberSourceEval[lang.Short](source)
    }
  }

  override def asIntSource(source: Source[Integer]): NumberSource[Integer] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Integer] = Numeric[lang.Integer]

    source match {
      case x: NumberSource[lang.Integer] => x
      case x: Source[lang.Integer] => new NumberSourceEval[lang.Integer](source)
    }
  }

  override def asLongSource(source: Source[lang.Long]): NumberSource[lang.Long] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Long] = Numeric[lang.Long]

    source match {
      case x: NumberSource[lang.Long] => x
      case x: Source[lang.Long] => new NumberSourceEval[lang.Long](source)
    }
  }

  override def asFloatSource(source: Source[lang.Float]): NumberSource[lang.Float] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Float] = Numeric[lang.Float]

    source match {
      case x: NumberSource[lang.Float] => x
      case x: Source[lang.Float] => new NumberSourceEval[lang.Float](source)
    }
  }

  override def asDoubleSource(source: Source[lang.Double]): NumberSource[lang.Double] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Double] = Numeric[lang.Double]

    source match {
      case x: NumberSource[lang.Double] => x
      case x: Source[lang.Double] => new NumberSourceEval[lang.Double](source)
    }
  }

  override def asBigIntegerSource(source: Source[BigInteger]): NumberSource[BigInteger] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[java.math.BigInteger] = Numeric[java.math.BigInteger]

    source match {
      case x: NumberSource[java.math.BigInteger] => x
      case x: Source[java.math.BigInteger] => new NumberSourceEval[java.math.BigInteger](source)
    }
  }

  override def asBigDecimalSource(source: Source[BigDecimal]): NumberSource[BigDecimal] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[java.math.BigDecimal] = Numeric[java.math.BigDecimal]

    source match {
      case x: NumberSource[java.math.BigDecimal] => x
      case x: Source[java.math.BigDecimal] => new NumberSourceEval[java.math.BigDecimal](source)
    }
  }

  override def asListSource[T](source: Source[util.List[T]]): ListSource[T] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    source match {
      case x: ListSourceEval[T] => x
      case x: Source[List[T]] => new ListSourceEval[T](x);
    }
  }

  override def asBooleanSource(source: Source[lang.Boolean]): BooleanSource = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    source match {
      case x: BooleanSource => x
      case x: Source[lang.Boolean] => new BooleanSourceEval(x)
    }
  }

  override def literal(literal: lang.Byte): NumberSource[lang.Byte] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Byte] = Numeric[lang.Byte]

    new NumberSourceEval[lang.Byte]((context: Context) => Opt(literal))
  }

  override def literal(literal: lang.Short): NumberSource[lang.Short] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[Short] = Numeric[Short]

    new NumberSourceEval[lang.Short]((context: Context) => Opt(literal))
  }

  override def literal(literal: Integer): NumberSource[Integer] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[Integer] = Numeric[Integer]

    new NumberSourceEval[Integer]((context: Context) => Opt(literal))
  }

  override def literal(literal: lang.Long): NumberSource[lang.Long] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Long] = Numeric[lang.Long]

    new NumberSourceEval[lang.Long]((context: Context) => Opt(literal))
  }

  override def literal(literal: lang.Float): NumberSource[lang.Float] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Float] = Numeric[lang.Float]

    new NumberSourceEval[lang.Float]((context: Context) => Opt(literal))
  }

  override def literal(literal: lang.Double): NumberSource[lang.Double] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[lang.Double] = Numeric[lang.Double]

    new NumberSourceEval[lang.Double]((context: Context) => Opt(literal))
  }

  override def literal(literal: BigInteger): NumberSource[BigInteger] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[BigInteger] = Numeric[BigInteger]

    new NumberSourceEval[BigInteger]((context: Context) => Opt(literal))
  }

  override def literal(literal: BigDecimal): NumberSource[BigDecimal] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Numeric[BigDecimal] = Numeric[BigDecimal]

    new NumberSourceEval[BigDecimal]((context: Context) => Opt(literal))
  }

  override def numberLiteral[T <: Number](supplier: Supplier[T]): NumberSource[T] = {
    val value: T = supplier.get();

    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    given num: Integral[T] = (value match {
      case x: lang.Byte => Integral[lang.Byte]
      case x: lang.Short => Integral[lang.Short]
      case x: lang.Integer => Integral[lang.Integer]
      case x: lang.Long => Integral[lang.Long]
      case x: lang.Float => Integral[lang.Float]
      case x: lang.Double => Integral[lang.Double]
      case x: java.math.BigInteger => Integral[java.math.BigInteger]
      case x: java.math.BigDecimal => Integral[java.math.BigDecimal]
    }).asInstanceOf[Integral[T]]

    new NumberSourceEval[T](context => Opt(value))
  }

  override def literal(value: String): StringSource = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    new StringSourceEval(context => Opt(value))
  }

  override def stringLiteral(supplier: Supplier[String]): StringSource = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    new StringSourceEval(context => Opt(supplier.get()))
  }

  override def literal(literal: lang.Boolean): BooleanSource = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    new BooleanSourceEval(context => Opt(literal))
  }

  override def booleanLiteral(supplier: Supplier[lang.Boolean]): BooleanSource = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    new BooleanSourceEval(context => Opt(supplier.get()))
  }

  override def literal[T](literal: util.List[T]): ListSource[T] = {
    given tracer: Tracer = Tracer.trace3()

    given dsl: BaseDsl = this

    new ListSourceEval[T](dsl.asInstanceOf[zDsl].literal(literal))
  }

  override def first[T](source: Source[util.List[T]]): T = ???
}

object BaseDsl {
  def javaListSource2ListSource[T](x: Source[util.List[T]]): Source[List[T]] = {
    given dsl: zDsl = x.dsl

    new AbstractEval[List[T]] {
      override def evalI(using context: Context): Opt[List[T]] = {
        import scala.jdk.CollectionConverters.given

        x.asInstanceOf[Eval[util.List[T]]].eval.map(e =>
          if (e != null) e.asScala.toList else List()
        )
      }
    }
  }

  def listSource2JavaListSource[T](x: Source[List[T]]): ListSource[T] = {
    given dsl: zDsl = x.dsl

    new ListSourceEval[T](null) {
      override def evalI(using context: Context): Opt[util.List[T]] = {
        import scala.jdk.CollectionConverters.given

        x.eval.map(e =>
          if (e != null) e.asJava else util.List.of()
        )
      }
    }
  }

  given integer2int: Conversion[Source[Integer], Source[Int]] with {
    override def apply(x: Source[Integer]): Source[Int] = {
      given dsl: zDsl = x.dsl

      new AbstractEval[Int]() {
        override def evalI(using context: Context): Opt[Int] = x.eval.map(_.toInt)
      }
    }
  }
}