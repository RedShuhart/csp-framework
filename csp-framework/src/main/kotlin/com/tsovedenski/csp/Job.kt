package com.tsovedenski.csp

import com.tsovedenski.csp.heuristics.ordering.comparators.VariableComparator
import com.tsovedenski.csp.heuristics.pruning.PruneSchema
import com.tsovedenski.csp.heuristics.pruning.Slice

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * Represents a problem being solved.
 *
 * @param V the type of variable identifiers.
 * @param D the type of domain values.
 * @param assignment initial assignment.
 * @param constraints constraints of the problem.
 */
data class Job <V, D> (val assignment: Assignment<V, D>, val constraints: List<Constraint<V, D>>) {
    var counter: Long = 0
        private set

    /**
     * Returns true if all [constraints] are satisfied for the current [assignment].
     */
    fun isValid(): Boolean
            = constraints.all { it(assignment) }

    /**
     * Returns true if no domain is [Empty] and all [constraints] that can be checked are satisfied.
     */
    fun isPartiallyValid(): Boolean = assignment.all { it.value !is Empty } &&
            constraints.asSequence().filter { it.canCheck(assignment) }.all { it(assignment) }

    /**
     * Returns true if all domains are [Selected].
     */
    fun isComplete() = assignment.isComplete()

    /**
     * Assigns [value] to [key].
     */
    fun assignVariable(key: V, value: Domain<D>)
            = apply { assignment[key] = value }

    // TODO: REMOVE THAT OR NOT THIS IS THE QUESTION
    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = assignment.filterIsInstance<V, Choice<D>>().entries.firstOrNull()

    /**
     * @return a [Slice] with current and future variables.
     */
    fun sliceAtCurrent(ordering: VariableComparator<V, D>): Slice<V> {
        val nextVariables = selectUnassignedVariables(ordering)
        return Slice (
                current = nextVariables.firstOrNull(),
                next = nextVariables.drop(1).toMutableSet(),
                previous = selectAssignedVariables().toMutableSet()
        )
    }

    /**
     * Prunes variables with the given pruning schema.
     */
    fun prune(slice: Slice<V>, pruningSchema: PruneSchema<V, D>): Assignment<V, D> {
        slice.current ?: return mutableMapOf()

        val mergedConstraints = constraints
                .filterIsInstance<AsBinaryConstraints<V, D>>()
                .flatMap(AsBinaryConstraints<V, D>::asBinaryConstraints)
        val sortedConstraints = pruningSchema(slice, mergedConstraints)

        return assignment.consistentWith(sortedConstraints, pruningSchema.direction)
    }

    /**
     * Merges assignments.
     */
    fun mergeAssignments(prunedAssignment: Assignment<V, D>) = assignment.putAll(prunedAssignment)

    fun step() = counter ++

    private fun selectAssignedVariables() =
            assignment.filterIsInstance<V, Selected<D>>().entries.map { it.key }

    private fun selectUnassignedVariables(ordering: VariableComparator<V, D>) =
            assignment.filterIsInstance<V, Choice<D>>().entries.asSequence().toSortedSet(Comparator { o1, o2 ->
                ordering(o1.key to o1.value, o2.key to o2.value, constraints).asInt
            }).map { it.key }

}
