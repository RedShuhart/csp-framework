package com.tsovedenski.csp.examples.queens

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
fun main(args: Array<String>) {
    val task = Queens(1)
    val solution = task.solve(strategy = Backtracking())
    solution.print()
    (solution as? Solved)?.let(::printQueens)
}