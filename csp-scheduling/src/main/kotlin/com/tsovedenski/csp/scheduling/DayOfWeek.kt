package com.tsovedenski.csp.scheduling

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
        val names = DayOfWeek.values().associateBy(DayOfWeek::asString)
        fun fromInt(key: Int) = values.getValue(key)
        fun fromString(name: String) = names.getValue(name)

    }

    fun toMinutes() = this.asInt * 24L * 60L
}
