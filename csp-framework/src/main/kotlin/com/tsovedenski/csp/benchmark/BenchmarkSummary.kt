package com.tsovedenski.csp.benchmark

import com.tsovedenski.csp.Statistics
import java.lang.StringBuilder

/**
 * Created by Tsvetan Ovedenski on 10/12/18.
 */
data class BenchmarkSummary (val entries: Map<String, Statistics>) {
    fun prettyPrint() {
        val longestKey = getLongestKey() ?: return
        val longestChecks = getLongestChecks() ?: return
        val longestTime = getLongestTime() ?: return
        val builder = StringBuilder()
        entries.forEach { s, entry ->
            builder.append(s)
            builder.append(" ".repeat(longestKey - s.length + 3))

            builder.append("Counter: ")
            builder.append(" ".repeat(longestChecks - entry.counter.toString().length))
            builder.append(entry.counter)
            builder.append(" checks")
            builder.append(" ".repeat(3))

            builder.append("Time: ")
            builder.append(" ".repeat(longestTime - entry.time.toString().length))
            builder.append(entry.time)
            builder.append(" ms")

            builder.appendln()
        }
        println(builder.toString())
    }

    private fun getLongestKey() = entries.keys.map(String::length).max()
    private fun getLongestChecks() = entries.values.asSequence().map(Statistics::counter).map(Long::toString).map(String::length).max()
    private fun getLongestTime() = entries.values.asSequence().map(Statistics::time).map(Long::toString).map(String::length).max()
}