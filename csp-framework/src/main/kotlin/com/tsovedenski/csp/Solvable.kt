package com.tsovedenski.csp

import com.tsovedenski.csp.strategies.Backtracking
import kotlin.system.measureTimeMillis

/**
 * Created by Tsvetan Ovedenski on 14/11/2018.
 */

/**
 * This interface allows it's implementor to be solved.
 */
interface Solvable <V, D> {

    /**
     * Define a CSP problem.
     *
     * @see [Problem].
     */
    fun toProblem(): Problem<V, D>
}

/**
 * Try to solve a [Solvable].
 *
 * @param strategy see [Strategy]
 * @return see [Solution]
 */
fun <V, D> Solvable<V, D>.solve(strategy: Strategy<V, D> = Backtracking<V, D>()): Solution<V, D>  {
    val problem = toProblem()
//    val assignment = problem.toAssignment().consistentWith(problem.constraints)
    val assignment = problem.toAssignment()
    val job = Job(assignment, problem.constraints)

    var solved: Job<V, D>? = null // TODO: Change to `val` once KT-27856 is done
    val time = measureTimeMillis {
        solved = strategy.run(job)
    }

    val solution = solved?.assignment?.toCompleteAssignment()
    return when (solution) {
        null -> NoSolution
        else -> Solved(solution, Statistics(solved!!.counter, time))
    }
}
