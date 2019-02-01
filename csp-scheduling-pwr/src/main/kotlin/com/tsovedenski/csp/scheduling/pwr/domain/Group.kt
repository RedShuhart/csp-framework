package com.tsovedenski.csp.scheduling.pwr.domain

import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
data class Group (
        val code: String,
        val day: DayOfWeek,
        val startAt: LocalTime,
        val endAt: LocalTime,
        val week: Week = Week.Both
) {
    constructor(code: String, day: DayOfWeek, range: String, week: Week = Week.Both)
            : this(code, day,
            LocalTime.of(range.split("-")[0].split(":")[0].toInt(), range.split("-")[0].split(":")[1].toInt()),
            LocalTime.of(range.split("-")[1].split(":")[0].toInt(), range.split("-")[1].split(":")[1].toInt()),
            week)

    companion object {
        private const val SEPARATOR = ";"
        private const val TIME_SEPARATOR = ":"
        fun fromString(value: String): Group {
            val parts = value.split(SEPARATOR)
            val startAt = parts[2].split(TIME_SEPARATOR).map(String::toInt)
            val endAt = parts[3].split(TIME_SEPARATOR).map(String::toInt)
            return Group(
                    parts[0],
                    DayOfWeek.valueOf(parts[1]),
                    LocalTime.of(startAt[0], startAt[1]),
                    LocalTime.of(endAt[0], endAt[1]),
                    Week.valueOf(parts[4])
            )
        }
    }

    fun overlaps(other: Group)
            = endAt.isAfter(other.startAt) && other.endAt.isAfter(startAt)

    fun difference(other: Group): Int {
        val list = listOf(this, other)
        val fst = list.minBy { it.startAt }!!
        val snd = list.maxBy { it.startAt }!!
        return (snd.startAt.toSecondOfDay() - fst.endAt.toSecondOfDay()) / 60
    }

    fun serialize() = listOf(
            code, day.toString(),
            "${startAt.hour}$TIME_SEPARATOR${startAt.minute}",
            "${endAt.hour}$TIME_SEPARATOR${endAt.minute}",
            week.toString()
    ).joinToString(SEPARATOR)
}