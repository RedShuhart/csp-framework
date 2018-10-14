package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
data class Task <V, D> (val variables: List<V>, val domain: List<D>, val constraints: List<Constraint<V, D>>)

fun <V, D> Task<V, D>.toAssignment(): Assignment<V, D>
        = variables.associate { it to Choice(domain) }.toMutableMap()