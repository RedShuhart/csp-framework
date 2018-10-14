package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
@Suppress("UNCHECKED_CAST")
internal data class Job <V, D> (val assignment: Assignment<V, D>, val task: Task<V, D>, var counter: Long = 0) {
    fun isValid(): Boolean
            = task.constraints.all { it(assignment) }

    fun isComplete(): Boolean
            = assignment.size == task.variables.size

    fun assignVariable(key: V, value: Variable<D>)
            = apply { assignment[key] = value }

    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = assignment.filterValues { it is Choice }.entries.firstOrNull() as Map.Entry<V, Choice<D>>?
}