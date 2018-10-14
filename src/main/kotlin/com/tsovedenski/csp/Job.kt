package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
@Suppress("UNCHECKED_CAST")
data class Job <V, D> (val solution: Assignment<V, D>, val task: Task<V, D>, var counter: Long = 0) {
    fun isValid(): Boolean
            = task.constraints.all { it(solution) }

    fun isComplete(): Boolean
            = solution.size == task.variables.size

    fun assignVariable(key: V, value: Variable<D>)
            = apply { solution[key] = value }

    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = solution.filterValues { it is Choice }.entries.firstOrNull() as Map.Entry<V, Choice<D>>?
}