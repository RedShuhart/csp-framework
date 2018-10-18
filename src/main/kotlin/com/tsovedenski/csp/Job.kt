package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
data class Job <V, D> (val assignment: Assignment<V, D>, val constraints: List<Constraint<V, D>>) {
    var counter: Long = 0
        private set

    fun isValid(): Boolean
            = constraints.all { it(assignment) }

    fun isPartiallyValid(): Boolean
            = constraints.asSequence().filter { it.canCheck(assignment) }.all { it(assignment) }

    fun isComplete() = assignment.isComplete()

    fun assignVariable(key: V, value: Variable<D>)
            = apply { assignment[key] = value }

    @Suppress("UNCHECKED_CAST")
    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = assignment.filterValues { it is Choice }.entries.firstOrNull() as Map.Entry<V, Choice<D>>?

    fun step() = counter ++

    fun duplicate() = copy(assignment = assignment.toMutableMap())
}