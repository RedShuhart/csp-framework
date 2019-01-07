package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.Job
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Strategy

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */

/**
 * (Slow) Generate-and-Test algorithm.
 *
 * Goes through each possible assignment until it finds one that
 * satisfies all constraints.
 * Guaranteed to find a solution if it exists.
 *
 * @param V the type of variable identifier.
 * @param D the type of domain value.
 */
class GenerateAndTest <V, D> : Strategy<V, D> {
    override fun run(job: Job<V, D>): Job<V, D>? {
        if (job.isComplete() && job.isValid()) return job

        job.step()

        var currentJob = job
        val variable = job.selectUnassignedVariable() ?: return null
        variable.value.values.forEach {
            val attempt = Selected(it)
            currentJob = currentJob.assignVariable(variable.key, attempt)
            val result = run(currentJob)
            if (result != null) {
                return result
            }
            currentJob = currentJob.assignVariable(variable.key, variable.value)
        }

        return null
    }
}
