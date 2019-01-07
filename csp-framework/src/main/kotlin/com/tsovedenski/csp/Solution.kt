package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * A solution of a problem.
 *
 * It can be either [Solved] or [NoSolution].
 *
 * @param V the type of variable identifier.
 * @param D the type of domain values.
 */
sealed class Solution <out V, out D> {

    /**
     * Pretty-print the solution.
     */
    abstract fun print()
}

/**
 * Represents a successful solution to a problem.
 *
 * @param assignment see [CompleteAssignment].
 * @param statistics see [Statistics].
 */
data class Solved <V, D> (val assignment: CompleteAssignment<V, D>, val statistics: Statistics) : Solution<V, D>() {
    override fun print() {
        assignment.print()
        statistics.print()
    }
}

/**
 * Represents the case when no solution was found.
 */
object NoSolution : Solution<Nothing, Nothing>() {
    override fun print() {
        println("No solution found")
    }
}
