package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 *
 * (Slow) Generate-and-Test algorithm
 */
object Backtracking : Strategy {
    override fun <V, D> run(job: Job<V, D>): Job<V, D>? {
        if (job.isComplete() && job.isValid()) return job

        job.step()

        var currentJob = job
        val variable = job.selectUnassignedVariable() ?: return null
        variable.value.values.forEach {
            val attempt = Selected(it)
            currentJob = currentJob.assignVariable(variable.key, attempt)
            if (currentJob.isPartiallyValid()) {
                val result = run(currentJob)
                if (result != null) {
                    return result
                }
            }
            currentJob = currentJob.assignVariable(variable.key, variable.value)
        }

        return null
    }
}