package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
sealed class Solution <out V, out D> {
    abstract fun print()
}

data class Solved <V, D> (val assignment: CompleteAssignment<V, D>, val statistics: Statistics) : Solution<V, D>() {
    override fun print() {
        assignment.print()
        statistics.print()
    }
}

object NoSolution : Solution<Nothing, Nothing>() {
    override fun print() {
        println("No solution found")
    }
}
