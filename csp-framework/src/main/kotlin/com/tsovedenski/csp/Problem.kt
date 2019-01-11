package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * A CSP problem definition.
 *
 * @constructor canonical CSP problem representation.
 * @param V the type of variable identifier.
 * @param D the type of domain value.
 * @property domains a map of variables and their domains.
 * @property constraints constraints that must be satisfied.
 */
data class Problem <V, D> (
    val domains: Map<V, List<D>>,
    val constraints: List<Constraint<V, D>>
): Solvable<V, D> {
    /**
     * @param variables variable identifiers.
     * @param domain one domain for all variables.
     * @param constraints list of constraints.
     */
    constructor(variables: List<V>, domain: List<D>, constraints: List<Constraint<V, D>>)
        : this(variables.withDomain(domain), constraints)

    /**
     * @param variables variable identifiers.
     * @param constraints list of constraints.
     * @param domainMapper function to map a variable to a domain.
     */
    constructor(variables: List<V>, constraints: List<Constraint<V, D>>, domainMapper: (V) -> List<D>)
        : this(variables.associate { it to domainMapper(it) }, constraints)

    override fun toProblem(): Problem<V, D> = this
}

internal fun <V, D> Problem<V, D>.toAssignment(): Assignment<V, D>
        = domains.mapValues { Domain.of(it.value) }.toMutableMap()
