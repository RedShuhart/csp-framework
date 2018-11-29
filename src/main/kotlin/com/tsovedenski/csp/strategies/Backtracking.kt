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
    private val pruneSchema: PruneSchema<V> = DefaultPruneSchema()
) : Strategy<V, D> {

    override fun run(job: Job<V, D>): Job<V, D>? {
        if (job.isComplete() && job.isValid()) return job
        job.step()

        // TODO currentVariable pass it as current variable ??
        // is it current ??
        // for FC shouldn't it be the next one?
        val variable = job.selectUnassignedVariable(variableOrdering) ?: return null


        variable.value.values.forEach {
            val attempt = Selected(it)
            job.assignVariable(variable.key, attempt)
            // job.prune(pruningSchema = )
            // TODO decide how to merge pruned assignments with initial assignments
            if (job.isPartiallyValid()) {
                val result = run(job)
                if (result != null) {
                    return result
                }
            }

            job.assignVariable(variable.key, variable.value)
        }

        return null
    }

}

//(a, b), constraints -> GT LE
