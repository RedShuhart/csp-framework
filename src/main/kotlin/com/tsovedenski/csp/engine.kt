package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
fun <V, D> solve(task: Task<V, D>): Assignment<V, D>? {
    val job = Job(task.toAssignment(), task)
    val solved = backtrack(job)
    solved?.counter?.let { println("Counter = $it") }
    return solved?.solution
}

fun <V, D> backtrack(job: Job<V, D>): Job<V, D>? {
    if (job.isValid()) return job
    job.counter += 1
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