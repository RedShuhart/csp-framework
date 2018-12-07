package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.ordering.comparators.VariableComparator
import com.tsovedenski.csp.heuristics.ordering.comparators.DefaultComparator
import com.tsovedenski.csp.heuristics.prouning.DefaultPruneSchema
import com.tsovedenski.csp.heuristics.prouning.PruneSchema

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 *
 * (Slow) Generate-and-Test algorithm
 */

class Backtracking <V, D> (
    private val variableOrdering: VariableComparator<V, D> = DefaultComparator(),
    private val pruneSchema: PruneSchema<V, D> = DefaultPruneSchema()
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
