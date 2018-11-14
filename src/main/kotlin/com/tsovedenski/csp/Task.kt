package com.tsovedenski.csp

import kotlin.system.measureTimeMillis

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
data class Task <V, D> (
    val domains: Map<V, List<D>>,
    val constraints: List<Constraint<V, D>>,
    open val initialAssignment: Map<V, Domain<D>> = mapOf() // TODO: Best if removed
): Solvable<V, D> {
    constructor(variables: List<V>, domain: List<D>, constraints: List<Constraint<V, D>>)
        : this(variables.withDomain(domain), constraints)

    constructor(variables: List<V>, constraints: List<Constraint<V, D>>, domainMapper: (V) -> List<D>)
        : this(variables.associate { it to domainMapper(it) }, constraints)

    val variables = domains.keys.toList() // TODO: Or leave it as set?

    override fun toTask(): Task<V, D> = this
}

fun <V, D> Task<V, D>.toAssignment(): Assignment<V, D> {
    val empty: Assignment<V, D> = domains.mapValues { Domain.of(it.value) }.toMutableMap()
//    initialAssignment.forEach { c, v -> empty[c] = v }
    initialAssignment.forEach { c, v -> empty.merge(c, v) { _, x -> x} }
    return empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>
//    return (empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>).also { it.forEach(::println) }
//    return empty
}

fun <V, D> Task<V, D>.solve(strategy: Strategy<V, D>): Solution<V, D> {
    val assignment = toAssignment().consistentWith(constraints)
    val job = Job(assignment, constraints)

    var solved: Job<V, D>? = null // TODO: Change to `val` with Kotlin 1.3
    val time = measureTimeMillis {
        solved = strategy.run(job)
    }

    return when (solved) {
        null -> NoSolution
        else -> Solved(solved!!.assignment, Statistics(solved!!.counter, time))
    }
}