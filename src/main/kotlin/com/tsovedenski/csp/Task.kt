package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
abstract class Task <V, D> {
    abstract val variables: List<V>
    abstract val domain: List<D>
    abstract val constraints: List<Constraint<V, D>>
    open val initialAssignment: Map<V, D> = mapOf()
}

fun <V, D> Task<V, D>.toAssignment(): Assignment<V, D> {
    val empty: Assignment<V, D> = variables.associate { it to Choice(domain) }.toMutableMap()
    initialAssignment.forEach { c, v -> empty[c] = Selected(v) }
    return empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>
}

data class GeneralTask <V, D> (
    override val variables: List<V>,
    override val domain: List<D>,
    override val constraints: List<Constraint<V, D>>
) : Task<V, D>()