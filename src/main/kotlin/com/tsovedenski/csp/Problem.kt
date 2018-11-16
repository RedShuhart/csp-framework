package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
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

fun <V, D> Problem<V, D>.toAssignment(): Assignment<V, D> {
    val empty: Assignment<V, D> = domains.mapValues { Domain.of(it.value) }.toMutableMap()
    return empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>
//    return (empty.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>).also { it.forEach(::println) }
//    return empty
}
