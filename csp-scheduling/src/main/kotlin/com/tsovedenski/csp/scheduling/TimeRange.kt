package com.tsovedenski.csp.scheduling

/**
 * Created by Ivan Yushchuk on 03/01/2019.
 */

data class TimeRange(val start: Boundary, val end: Boundary) {

    fun overlaps(range: TimeRange) = end.minutes >= range.start.minutes && range.end.minutes >= start.minutes

    fun print() = "${start.print()}-${end.print()}"

    companion object {
        fun fromString(text: String): TimeRange {
            val split = text.split("-")
            return TimeRange(Boundary.fromString(split[0]), Boundary.fromString(split[1]))
        }
    }
}

fun areOverlapping(r1: TimeRange, r2: TimeRange) = r1.overlaps(r2)
fun areNotOverlapping(r1: TimeRange, r2: TimeRange) = !r1.overlaps(r2)
