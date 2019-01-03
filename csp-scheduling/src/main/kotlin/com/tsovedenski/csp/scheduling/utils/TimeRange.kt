package com.tsovedenski.csp.scheduling.utils

/**
 * Created by Ivan Yushchuk on 03/01/2019.
 */

data class TimeRange(val start: Boundary, val end: Boundary) {

    fun overlaps(range: TimeRange) = end.time >= range.start.time && range.end.time >= start.time

    fun print() = "${start.print()} - ${end.print()}"

}

fun areOverlapping(r1: TimeRange, r2: TimeRange) = r1.overlaps(r2)
