package io.github.mathter.jmorph.dsl.base

import java.lang
import java.lang.Short
import java.math.{BigDecimal, BigInteger}

object JavaNumeric {
  implicit object ByteNumeric extends Integral[lang.Byte] {
    override def quot(x: lang.Byte, y: lang.Byte): lang.Byte = (x.byteValue() / y.byteValue()).byteValue

    override def rem(x: lang.Byte, y: lang.Byte): lang.Byte = (x.byteValue() % y.byteValue()).byteValue

    override def plus(x: lang.Byte, y: lang.Byte): lang.Byte = (x.byteValue() + y.byteValue()).byteValue

    override def minus(x: lang.Byte, y: lang.Byte): lang.Byte = (x.byteValue() - y.byteValue()).byteValue

    override def times(x: lang.Byte, y: lang.Byte): lang.Byte = (x.byteValue() * y.byteValue()).byteValue

    override def negate(x: lang.Byte): lang.Byte = (0 - x).byteValue

    override def fromInt(x: Int): lang.Byte = x.byteValue

    override def parseString(str: String): Option[lang.Byte] = Option(lang.Byte.parseByte(str))

    override def toInt(x: lang.Byte): Int = x.toInt

    override def toLong(x: lang.Byte): Long = x.toLong

    override def toFloat(x: lang.Byte): Float = x.toFloat

    override def toDouble(x: lang.Byte): Double = x.toDouble

    override def compare(x: lang.Byte, y: lang.Byte): Int = x.compareTo(y.byteValue())
  }

  implicit object ShortNumeric extends Integral[Short] {
    override def quot(x: Short, y: Short): Short = (x.shortValue() / y.shortValue()).shortValue

    override def rem(x: Short, y: Short): Short = (x.shortValue() % y.shortValue()).shortValue

    override def plus(x: Short, y: Short): Short = (x.shortValue() + y.shortValue()).shortValue

    override def minus(x: Short, y: Short): Short = (x.shortValue() - y.shortValue()).shortValue

    override def times(x: Short, y: Short): Short = (x.shortValue() * y.shortValue()).shortValue

    override def negate(x: Short): Short = (0 - x).shortValue

    override def fromInt(x: Int): Short = x.shortValue

    override def parseString(str: String): Option[Short] = Option(Short.parseShort(str))

    override def toInt(x: Short): Int = x.toInt

    override def toLong(x: Short): Long = x.toLong

    override def toFloat(x: Short): Float = x.toFloat

    override def toDouble(x: Short): Double = x.toDouble

    override def compare(x: Short, y: Short): Int = x.compareTo(y.shortValue())
  }

  implicit object IntegerNumeric extends Integral[Integer] {
    override def quot(x: Integer, y: Integer): Integer = x.intValue() / y.intValue()

    override def rem(x: Integer, y: Integer): Integer = x.intValue() % y.intValue()

    override def plus(x: Integer, y: Integer): Integer = x.intValue() + y.intValue()

    override def minus(x: Integer, y: Integer): Integer = x.intValue() - y.intValue()

    override def times(x: Integer, y: Integer): Integer = x.intValue() * y.intValue()

    override def negate(x: Integer): Integer = -x.intValue()

    override def fromInt(x: Int): Integer = x

    override def parseString(str: String): Option[Integer] = Option(Integer.parseInt(str))

    override def toInt(x: Integer): Int = x

    override def toLong(x: Integer): Long = x.toLong

    override def toFloat(x: Integer): Float = x.toFloat

    override def toDouble(x: Integer): Double = x.toDouble

    override def compare(x: Integer, y: Integer): Int = x.compareTo(y.intValue())
  }

  implicit object LongNumeric extends Integral[lang.Long] {
    override def quot(x: lang.Long, y: lang.Long): lang.Long = x.longValue() / y.longValue()

    override def rem(x: lang.Long, y: lang.Long): lang.Long = x.longValue() % y.longValue()

    override def plus(x: lang.Long, y: lang.Long): lang.Long = x.longValue() + y.longValue()

    override def minus(x: lang.Long, y: lang.Long): lang.Long = x.longValue() - y.longValue()

    override def times(x: lang.Long, y: lang.Long): lang.Long = x.longValue() * y.longValue()

    override def negate(x: lang.Long): lang.Long = -x.longValue()

    override def fromInt(x: Int): lang.Long = x.toLong

    override def parseString(str: String): Option[lang.Long] = Option(lang.Long.parseLong(str))

    override def toInt(x: lang.Long): Int = x.toInt

    override def toLong(x: lang.Long): Long = x.toLong

    override def toFloat(x: lang.Long): Float = x.toFloat

    override def toDouble(x: lang.Long): Double = x.toDouble

    override def compare(x: lang.Long, y: lang.Long): Int = x.compareTo(y.longValue())
  }

  implicit object FloatNumeric extends Integral[lang.Float] {
    override def quot(x: lang.Float, y: lang.Float): lang.Float = x.floatValue() / y.floatValue()

    override def rem(x: lang.Float, y: lang.Float): lang.Float = x.floatValue() % y.floatValue()

    override def plus(x: lang.Float, y: lang.Float): lang.Float = x.floatValue() + y.floatValue()

    override def minus(x: lang.Float, y: lang.Float): lang.Float = x.floatValue() - y.floatValue()

    override def times(x: lang.Float, y: lang.Float): lang.Float = x.floatValue() * y.floatValue()

    override def negate(x: lang.Float): lang.Float = -x.floatValue()

    override def fromInt(x: Int): lang.Float = x.toFloat

    override def parseString(str: String): Option[lang.Float] = Option(lang.Float.parseFloat(str))

    override def toInt(x: lang.Float): Int = x.toInt

    override def toLong(x: lang.Float): Long = x.toLong

    override def toFloat(x: lang.Float): Float = x.toFloat

    override def toDouble(x: lang.Float): Double = x.toDouble

    override def compare(x: lang.Float, y: lang.Float): Int = x.compareTo(y.floatValue())
  }

  implicit object DoubleNumeric extends Integral[lang.Double] {
    override def quot(x: lang.Double, y: lang.Double): lang.Double = x.floatValue() / y.floatValue()

    override def rem(x: lang.Double, y: lang.Double): lang.Double = x.floatValue() % y.floatValue()

    override def plus(x: lang.Double, y: lang.Double): lang.Double = x.floatValue() + y.floatValue()

    override def minus(x: lang.Double, y: lang.Double): lang.Double = x.floatValue() - y.floatValue()

    override def times(x: lang.Double, y: lang.Double): lang.Double = x.floatValue() * y.floatValue()

    override def negate(x: lang.Double): lang.Double = -x.doubleValue()

    override def fromInt(x: Int): lang.Double = x.toDouble

    override def parseString(str: String): Option[lang.Double] = Option(lang.Double.parseDouble(str))

    override def toInt(x: lang.Double): Int = x.toInt

    override def toLong(x: lang.Double): Long = x.toLong

    override def toFloat(x: lang.Double): Float = x.toFloat

    override def toDouble(x: lang.Double): Double = x.toDouble

    override def compare(x: lang.Double, y: lang.Double): Int = x.compareTo(y.doubleValue())
  }

  implicit object BigIntegerNumeric extends Integral[BigInteger] {
    override def quot(x: BigInteger, y: BigInteger): BigInteger = x.divide(y)

    override def rem(x: BigInteger, y: BigInteger): BigInteger = x.remainder(y)

    override def plus(x: BigInteger, y: BigInteger): BigInteger = x.add(y)

    override def minus(x: BigInteger, y: BigInteger): BigInteger = x.subtract(y)

    override def times(x: BigInteger, y: BigInteger): BigInteger = x.multiply(y)

    override def negate(x: BigInteger): BigInteger = x.negate()

    override def fromInt(x: Int): BigInteger = BigInteger.valueOf(x)

    override def parseString(str: String): Option[BigInteger] = Option(new BigInteger(str))

    override def toInt(x: BigInteger): Int = x.toInt

    override def toLong(x: BigInteger): Long = x.toLong

    override def toFloat(x: BigInteger): Float = x.toFloat

    override def toDouble(x: BigInteger): Double = x.toDouble

    override def compare(x: BigInteger, y: BigInteger): Int = x.compareTo(y)
  }

  implicit object BigDecimalNumeric extends Integral[BigDecimal] {
    override def quot(x: BigDecimal, y: BigDecimal): BigDecimal = x.divide(y)

    override def rem(x: BigDecimal, y: BigDecimal): BigDecimal = x.remainder(y)

    override def plus(x: BigDecimal, y: BigDecimal): BigDecimal = x.add(y)

    override def minus(x: BigDecimal, y: BigDecimal): BigDecimal = x.subtract(y)

    override def times(x: BigDecimal, y: BigDecimal): BigDecimal = x.multiply(y)

    override def negate(x: BigDecimal): BigDecimal = x.negate()

    override def fromInt(x: Int): BigDecimal = BigDecimal.valueOf(x)

    override def parseString(str: String): Option[BigDecimal] = Option(new BigDecimal(str))

    override def toInt(x: BigDecimal): Int = x.toInt

    override def toLong(x: BigDecimal): Long = x.toLong

    override def toFloat(x: BigDecimal): Float = x.toFloat

    override def toDouble(x: BigDecimal): Double = x.toDouble

    override def compare(x: BigDecimal, y: BigDecimal): Int = x.compareTo(y)
  }
}
