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
package io.github.mathter.morph.conv

import java.time.format.DateTimeFormatter.{ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME, ISO_OFFSET_DATE, ISO_OFFSET_DATE_TIME, ISO_OFFSET_TIME, ISO_ZONED_DATE_TIME}
import java.time.{LocalDate, LocalDateTime, LocalTime, OffsetDateTime, OffsetTime, ZoneOffset, ZonedDateTime}

object DateTimeConv {
  private val localDate = ISO_LOCAL_DATE

  private val localDateTime = ISO_LOCAL_DATE_TIME

  private val zonedDateTime = ISO_ZONED_DATE_TIME

  private val offsetDateTime = ISO_OFFSET_DATE_TIME

  private val offsetDate = ISO_OFFSET_DATE

  private val offsetTime = ISO_OFFSET_TIME

  def string2localDate(x: String): LocalDate = {
    if (x != null) LocalDate.parse(x, this.localDate) else null
  }

  def localDate2string(x: LocalDate): String = {
    if (x != null) this.localDate.format(x) else null
  }

  def string2localDateTime(x: String): LocalDateTime = {
    if (x != null) LocalDateTime.parse(x, this.localDateTime) else null
  }

  def localDateTime2string(x: LocalDateTime): String = {
    if (x != null) this.localDateTime.format(x) else null
  }

  def string2zonedDateTime(x: String): ZonedDateTime = {
    if (x != null) ZonedDateTime.parse(x, this.zonedDateTime) else null
  }

  def zonedDateTime2string(x: ZonedDateTime): String = {
    if (x != null) this.zonedDateTime.format(x) else null
  }

  def string2offsetDateTime(x: String): OffsetDateTime = {
    if (x != null) OffsetDateTime.parse(x, this.offsetDateTime) else null
  }

  def offsetDateTime2string(x: OffsetDateTime): String = {
    if (x != null) this.offsetDateTime.format(x) else null
  }

  def string2offsetDate(x: String): OffsetDateTime = {
    if (x != null) {
      val temporalAccessor = this.offsetDate.parse(x)
      val localDate = LocalDate.from(temporalAccessor)
      val localTime = LocalTime.of(0, 0)
      val zoneOffset = ZoneOffset.from(temporalAccessor)

      OffsetDateTime.of(localDate, localTime, zoneOffset)
    } else {
      null
    }
  }

  def offsetDate2string(x: OffsetDateTime): String = {
    if (x != null) this.offsetDate.format(x) else null
  }

  def string2offsetTime(x: String): OffsetTime = {
    if (x != null) OffsetTime.parse(x, this.offsetTime) else null
  }

  def offsetTime2string(x: OffsetTime): String = {
    if (x != null) this.offsetTime.format(x) else null
  }
}
