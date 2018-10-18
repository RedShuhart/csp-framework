package com.tsovedenski.csp

import kotlin.system.measureTimeMillis

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
abstract class Task <V, D> {
    abstract val variables: List<V>
    abstract val domain: List<D>
    abstract val constraints: List<Constraint<V, D>>
    open val initialAssignment: Map<V, Variable<D>> = mapOf()
}

fun <V, D> Task<V, D>.toAssignment(): Assignment<V, D> {
    val empty: Assignment<V, D> = variables.associate { it to Choice(domain) }.toMutableMap()
//    initialAssignment.forEach { c, v -> empty[c] = v }
    initialAssignment.forEach { c, v -> empty.merge(c, v) { _, x -> x} }
//    return empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>
    return (empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>).also { it.forEach(::println) }
//    return empty
}

fun <V, D> Task<V, D>.solve(strategy: Strategy): Solution<V, D> {
    val job = Job(toAssignment(), constraints)

    var solved: Job<V, D>? = null // TODO: Change to `val` with Kotlin 1.3
    val time = measureTimeMillis {
        solved = strategy.run(job)
    }

    return when (solved) {
        null -> NoSolution
        else -> Solved(solved!!.assignment, Statistics(solved!!.counter, time))
    }
}

data class GeneralTask <V, D> (
    override val variables: List<V>,
    override val domain: List<D>,
    override val constraints: List<Constraint<V, D>>
) : Task<V, D>()