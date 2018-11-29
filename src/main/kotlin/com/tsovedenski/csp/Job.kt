package com.tsovedenski.csp

import com.tsovedenski.csp.heuristics.ordering.comparators.VariableComparator
import com.tsovedenski.csp.heuristics.prouning.PruneSchema
import com.tsovedenski.csp.heuristics.prouning.Slice

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

    fun assignVariable(key: V, value: Domain<D>)
            = apply { assignment[key] = value }

    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = assignment.filterIsInstance<V, Choice<D>>().entries.firstOrNull()

    fun sliceAtCurrent(ordering: VariableComparator<V, D>): Slice<V> {
        val nextVariables = selectUnassignedVariables(ordering)
        return Slice (
                current = nextVariables.firstOrNull(),
                next = nextVariables.drop(1).toSet(),
                previous = assignment.filterIsInstance<V, Selected<D>>().keys
        )
    }

    fun prune(slice: Slice<V>, pruningSchema: PruneSchema<V>): Assignment<V, D> {
        slice.current ?: return mutableMapOf()
        val variablesToPrune =  pruningSchema(slice.current, slice.previous, slice.next)
        return (assignment.filter { variablesToPrune.contains(it.key) } as Assignment<V, D>).consistentWith(constraints)
    }

    fun step() = counter ++

    fun duplicate() = copy(assignment = assignment.toMutableMap())

    private fun selectUnassignedVariables(ordering: VariableComparator<V, D>) =
            assignment.filterIsInstance<V, Choice<D>>().entries.asSequence().toSortedSet(Comparator { o1, o2 ->
                ordering(o1.key to o1.value, o2.key to o2.value, constraints).asInt
            }).map{ it.key }

}
