package com.tsovedenski.csp.scheduling.pwr.domain

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
data class Subject(val name: String, val type: Type) {
    val abbr get() = name.split(" ").map { it.first() }.joinToString("")
}