package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.ordering.comparators.DefaultComparator
import com.tsovedenski.csp.heuristics.ordering.comparators.VariableComparator
import com.tsovedenski.csp.heuristics.pruning.DefaultPruneSchema
import com.tsovedenski.csp.heuristics.pruning.PruneSchema

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */

/**
 * Classical backtracking algorithm.
 *
 * Supports variable ordering and pruning.
 *
 * @param V the type of variable identifier.
 * @param D the type of domain value.
 * @constructor Creates instance, duh.
 * @param variableOrdering order of variables (see [VariableComparator]).
 * @param pruneSchema how to prune variables (see [PruneSchema]).
 * @param callback called when assignment has been made.
 * @see VariableComparator
 * @see PruneSchema
 */
class Backtracking <V, D> (
    protected val variableOrdering: VariableComparator<V, D> = DefaultComparator(),
    protected val pruneSchema: PruneSchema<V, D> = DefaultPruneSchema(),
    protected val callback: (Assignment<V, D>) -> Unit = {}
) : Strategy<V, D> {

    override fun run(job: Job<V, D>): Job<V, D>? {
        if (job.isComplete() && job.isValid()) return job
        job.step()

        val slice = job.sliceAtCurrent(variableOrdering)
        val current = slice.current ?: return null
        val choice = job.assignment[current] as Choice<D>

        val originalAssignments = job.assignment.toMutableMap()

        choice.values.forEach {
            val attempt = Selected(it)
            job.assignVariable(current, attempt)
            val pruned = job.prune(slice, pruneSchema)
            job.mergeAssignments(pruned)
            callback(job.assignment)
            if (job.isPartiallyValid()) {
                val result = run(job)
                if (result != null) {
                    return result
                }
            }
            job.mergeAssignments(originalAssignments)
        }

        return null
    }

}

//(a, b), constraints -> GT LE
