package io.github.mathter.morph.conv

import java.util.UUID

object CommonConv {
  def string2uuid(x: String): UUID = if (x != null) UUID.fromString(x) else null

  def uuid2string(x: UUID): String = if (x != null) x.toString else null
}
