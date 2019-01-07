package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * A CSP problem definition.
 *
 * @param V the type of variable identifier.
 * @param D the type of domain value.
 * @param domains a map of variables and their domains.
 * @param constraints constraints that must be satisfied.
 */
data class Problem <V, D> (
    val domains: Map<V, List<D>>,
    val constraints: List<Constraint<V, D>>
): Solvable<V, D> {
    constructor(variables: List<V>, domain: List<D>, constraints: List<Constraint<V, D>>)
        : this(variables.withDomain(domain), constraints)

    constructor(variables: List<V>, constraints: List<Constraint<V, D>>, domainMapper: (V) -> List<D>)
        : this(variables.associate { it to domainMapper(it) }, constraints)

    override fun toProblem(): Problem<V, D> = this
}

fun <V, D> Problem<V, D>.toAssignment(): Assignment<V, D>
        = domains.mapValues { Domain.of(it.value) }.toMutableMap()
