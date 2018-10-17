package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.Choice
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Strategy

/**
 * Created by Tsvetan Ovedenski on 17/10/18.
 */
object ForwardChecking : Strategy {
    override fun <V, D> run(job: Job<V, D>): Job<V, D>? {
        job.step()
        if (job.isComplete() && job.isValid()) return job

        var dup = job
        val variable = job.selectUnassignedVariable() ?: return null
        variable.value.values.forEach {
            val attempt = Selected(it)
            dup = dup.duplicate()
            dup.assignVariable(variable.key, attempt)
            val tempAssignment = dup.assignment.toMutableMap()
            dup.assignment.mapValues { m ->
                if (m.value is Choice) {
                    Choice((m.value as Choice).values.filter { v -> v != attempt.value })
                } else {
                    m.value
                }
            }
            val result = run(dup)
            if (result != null) {
                return result
            }
            dup.assignVariable(variable.key, variable.value)
            dup = dup.copy(assignment = tempAssignment)
        }
        return null
    }
}