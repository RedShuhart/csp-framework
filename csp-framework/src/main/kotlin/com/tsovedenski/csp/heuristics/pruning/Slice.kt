package com.tsovedenski.csp.heuristics.pruning

/**
 * Slice that containts current variable and a set of future variables.
 *
 * @param V the type of variable identifier.
 * @param current current variable. If null - the [Job.assignment] is complete.
 * @param next future variables.
 */
data class Slice<V> (
    val current: V?,
    val next: Set<V>
)