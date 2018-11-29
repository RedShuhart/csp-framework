package com.tsovedenski.csp

import com.tsovedenski.csp.heuristics.ordering.comparators.VariableComparator
import com.tsovedenski.csp.heuristics.prouning.PruneSchema

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

    fun selectUnassignedVariable(ordering: VariableComparator<V, D>): Map.Entry<V, Choice<D>>? =
            assignment.filterIsInstance<V, Choice<D>>().entries
            .sortedWith(Comparator { o1, o2 ->
                ordering(o1.key to o1.value, o2.key to o2.value, constraints).asInt
            })
            .firstOrNull()

    fun prune(currentVar: Map.Entry<V, Choice<D>>, pruningSchema: PruneSchema<V>): Assignment<V, D> {

        val previousVariables: Set<V> = assignment.filterIsInstance<V, Selected<D>>().keys
        val nextVariables: Set<V> = assignment.filterIsInstance<V, Choice<D>>().keys
        val currentVariable: V = currentVar.key

        val variablesToPrune =  pruningSchema(currentVariable, previousVariables, nextVariables)

        return (assignment.filter { variablesToPrune.contains(it.key) } as Assignment<V, D>).consistentWith(constraints)
    }

    fun step() = counter ++

    fun duplicate() = copy(assignment = assignment.toMutableMap())

}
