package com.tsovedenski.csp.scheduling

/**
 * Created by Ivan Yushchuk on 03/01/2019.
 */

data class Boundary(val minutes: Long) {
    constructor(dayOfWeek: DayOfWeek, time: Long): this(dayOfWeek.toMinutes() + time )
    constructor(dayOfWeek: DayOfWeek, time: String): this(dayOfWeek.toMinutes() + timeStringToMillis(time))
    constructor(dayOfWeek: Int, time: String): this(DayOfWeek.fromInt(dayOfWeek).toMinutes() + timeStringToMillis(time))
    constructor(dayOfWeek: Int, time: Long): this(DayOfWeek.fromInt(dayOfWeek).toMinutes() + time)

    fun print(): String {
        val days = minutes /  1440L
        val hours = (minutes - days * 1440) / 60L
        val minutes = (minutes - days * 1440L - hours * 60L)
        return "${DayOfWeek.fromInt(days.toInt()).asString} $hours:$minutes"
    }

    companion object {
        //Algebra_Tuesday 8:30-Tuesday 9:30;Tuesday 8:30-Tuesday 9:45;
        fun fromString(text: String): Boundary {
            val split = text.split(" ")
            return Boundary(DayOfWeek.fromString(split[0]), timeStringToMillis(split[1]))
        }
    }
}

fun timeStringToMillis(time: String): Long {
    val split = time.split(":")
    return split[0].toLong() * 60L + split[1].toLong()
}
