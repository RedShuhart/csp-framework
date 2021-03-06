package com.tsovedenski.csp

import java.text.NumberFormat
import java.util.*

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * Statistics after a solution has been found.
 *
 * @property counter how many nodes were visited.
 * @property time how many milliseconds it's taken.
 */
data class Statistics (val counter: Long, val time: Long) {
    private val formatter = NumberFormat.getInstance(Locale("pl", "PL"))
    fun print(oneline: Boolean = false) {
        println(pretty(oneline))
    }
    fun pretty(oneline: Boolean): String {
        val counterF = formatter.format(counter)
        val timeF = formatter.format(time)
        val builder = StringBuilder()
        if (oneline) {
            builder.append("Counter: $counterF checks\tTime: $timeF ms")
        } else {
            builder.appendln("+---- Statistics")
            builder.appendln("| Counter: $counterF checks")
            builder.appendln("|    Time: $timeF ms")
            builder.append("+---------------")
        }
        return builder.toString()
    }
    fun csv(): String {
        val counterF = formatter.format(counter)
        val timeF = formatter.format(time)
        return "$counterF,$timeF"
    }
    companion object {
        val ZERO = Statistics(-1, -1)
    }
}