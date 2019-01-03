package com.tsovedenski.csp.scheduling.utils

/**
 * Created by Ivan Yushchuk on 03/01/2019.
 */

data class Boundary(val time: Long) {
    constructor(dayOfWeek: DayOfWeek, time: Long): this(dayOfWeek.toMillis() + time )
    constructor(dayOfWeek: DayOfWeek, time: String): this(dayOfWeek.toMillis() + timeStringToMillis(time))
    constructor(dayOfWeek: Int, time: String): this(DayOfWeek.fromInt(dayOfWeek).toMillis() + timeStringToMillis(time))
    constructor(dayOfWeek: Int, time: Long): this(DayOfWeek.fromInt(dayOfWeek).toMillis() + time)

    fun print(): String {
        val days = time /  86400000L
        val hours = (time - days * 86400000L) / 3600000L
        val minutes = (time - days * 86400000L - hours * 3600000L) / 60000L
        return "${DayOfWeek.fromInt(days.toInt()).asString} $hours:$minutes"
    }
}

fun timeStringToMillis(time: String): Long {
    val split = time.split(":")
    return split[0].toLong() * 3600000L + split[1].toLong() * 60000L
}
