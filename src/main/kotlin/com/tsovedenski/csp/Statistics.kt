package com.tsovedenski.csp

import java.text.NumberFormat
import java.util.*

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
data class Statistics (val counter: Long, val time: Long) {
    private val formatter = NumberFormat.getInstance(Locale("pl", "PL"))
    fun print() {
        println("+---- Statistics")
        println("| Counter: ${formatter.format(counter)} checks")
        println("|    Time: ${formatter.format(time)} ms")
        println("+---------------")
    }
}