package com.tsovedenski.csp.heuristics.prouning

data class Slice<V> (
        val current: V?,
        val next: Set<V>
)
