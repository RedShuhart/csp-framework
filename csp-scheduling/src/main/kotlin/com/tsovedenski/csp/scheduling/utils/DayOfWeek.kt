package com.tsovedenski.csp.scheduling.utils

/**
 * Created by Ivan Yushchuk on 03/01/2019.
 */


enum class DayOfWeek(val asInt: Int, val asString: String) {
    MON(0, "Monday"),
    TUE(1, "Tuesday"),
    WED(2, "Wednesday"),
    THU(3, "Thursday"),
    FRI(4, "Friday"),
    SAT(5, "Saturday"),
    SUN(6, "Sunday");

    companion object {
        val values = DayOfWeek.values().associateBy(DayOfWeek::asInt)
        fun fromInt(key: Int) = values.getValue(key)
    }

    fun toMillis() = this.asInt * 86400000L
}
