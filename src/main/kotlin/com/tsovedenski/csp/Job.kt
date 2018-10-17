package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
data class Job <V, D> (val assignment: Assignment<V, D>, val constraints: List<Constraint<V, D>>) {
    var counter: Long = 0
        private set

    fun isValid(): Boolean
            = constraints.all { it(assignment) }

    fun isComplete() = assignment.isComplete()

    fun assignVariable(key: V, value: Variable<D>)
            = apply { assignment[key] = value }

    fun removeChoice(value: D): Set<V> {
        val changed = mutableSetOf<V>()
        assignment.keys.forEach { key ->
            val choice = assignment[key] as? Choice
            if (choice != null) {
                val filtered = choice.values.filter { it != value }
                assignment[key] = when (filtered.size) {
                    1 -> Selected(filtered.first())
                    else -> Choice(filtered)
                }
                changed.add(key)
            }
        }
        return changed
    }

    fun addChoice(value: D, keys: Set<V>) = apply {
//        assignment.keys.forEach { key ->
//            val choice = assignment[key] as? Choice
//            if (choice != null) {
//                assignment[key] = choice.copy(values = choice.values + value)
//            }
//        }
        keys.forEach { key ->
            val choice = assignment[key]
            assignment[key] = when (choice) {
                is Selected -> Choice(listOf(choice.value, value))
                is Choice -> Choice(choice.values + value)
                else -> TODO()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = assignment.filterValues { it is Choice }.entries.firstOrNull() as Map.Entry<V, Choice<D>>?

    fun step() = counter ++

    fun duplicate() = copy(assignment = assignment.toMutableMap())
}