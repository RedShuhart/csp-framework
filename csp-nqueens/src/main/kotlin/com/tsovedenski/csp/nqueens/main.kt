package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.heuristics.prouning.schemas.ForwardChecking
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
fun main(args: Array<String>) {
    val problem = Queens(10)
    val solution = problem.solve(
        strategy = Backtracking(
//            variableOrdering = SmallestDomainVariable(),
            pruneSchema = ForwardChecking()
        )
    )
    solution.print()
    (solution as? Solved)?.let(::printQueens)
}