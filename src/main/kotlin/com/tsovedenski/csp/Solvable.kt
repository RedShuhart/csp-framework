package com.tsovedenski.csp

import com.tsovedenski.csp.strategies.Backtracking
import kotlin.system.measureTimeMillis

/**
 * Created by Tsvetan Ovedenski on 14/11/2018.
 */
interface Solvable <V, D> {
    fun toTask(): Task<V, D>
}

fun <V, D> Solvable<V, D>.solve(strategy: Strategy<V, D> = Backtracking<V, D>()): Solution<V, D>  {
    val task = toTask()
    val assignment = task.toAssignment().consistentWith(task.constraints)
    val job = Job(assignment, task.constraints)

    var solved: Job<V, D>? = null // TODO: Change to `val` with Kotlin 1.3
    val time = measureTimeMillis {
        solved = strategy.run(job)
    }

    return when (solved) {
        null -> NoSolution
        else -> Solved(solved!!.assignment, Statistics(solved!!.counter, time))
    }
}
