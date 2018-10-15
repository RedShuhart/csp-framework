package com.tsovedenski.csp

import kotlin.system.measureTimeMillis

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
fun <V, D> solve(task: Task<V, D>): Solution<V, D> {
    val job = Job(task.toAssignment(), task)
    job.assignment.print()
//    (job.assignment.toList().sortedBy { it.second !is Selected }.toMap() as Assignment<V, D>).print()
    var solved: Job<V, D>? = null
    val time = measureTimeMillis {
        solved = backtrack(job)
    }
    return when (solved) {
        null -> NoSolution
        else -> Solved(solved!!.assignment, Statistics(solved!!.counter, time))
    }
}

internal fun <V, D> backtrack(job: Job<V, D>): Job<V, D>? {
    if (job.isValid()) return job
    job.step()
    var currentJob = job
    val variable = job.selectUnassignedVariable() ?: return null
    variable.value.values.forEach {
        val attempt = Selected(it)
        currentJob = currentJob.assignVariable(variable.key, attempt)
        val result = backtrack(currentJob)
        if (result != null) {
            return result
        }
        currentJob = currentJob.assignVariable(variable.key, variable.value)
    }

    return null
}