package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
data class Statistics (val counter: Long) {
    fun print() {
        println("+---- Statistics")
        println("| Counter: $counter")
        println("+---------------")
    }
}