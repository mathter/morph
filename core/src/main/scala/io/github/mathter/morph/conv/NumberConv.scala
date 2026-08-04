package io.github.mathter.morph.conv

import java.lang

object NumberConv {

  def string2byte(x: String): Byte | Null = if (x != null) lang.Byte.parseByte(x) else null

  def byte2string(x: Byte | Null): String = if (x != null) x.toString else null

  def string2short(x: String): Short | Null = if (x != null) lang.Short.parseShort(x) else null

  def short2string(x: Short | Null): String = if (x != null) x.toString else null

  def string2int(x: String): Int | Null = if (x != null) lang.Integer.parseInt(x) else null

  def int2string(x: Int | Null): String = if (x != null) x.toString else null

  def string2long(x: String): Long | Null = if (x != null) lang.Long.parseLong(x) else null

  def long2string(x: Long | Null): String = if (x != null) x.toString else null

  def string2float(x: String): Float | Null = if (x != null) lang.Float.parseFloat(x) else null

  def float2string(x: Float | Null): String = if (x != null) x.toString else null

  def string2double(x: String): Double | Null = if (x != null) lang.Double.parseDouble(x) else null

  def double2string(x: Double | Null): String = if (x != null) x.toString else null

  def string2bigInt(x: String): BigInt = if (x != null) BigInt(x) else null

  def bigInt2string(x: BigInt) = if (x != null) x.toString else null

  def string2bigDecimal(x: String): BigDecimal = if (x != null) BigDecimal(x) else null

  def bigDecimal2string(x: BigDecimal): String = if (x != null) x.toString else null
}
