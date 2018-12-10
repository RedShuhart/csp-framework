package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.benchmark.Benchmark
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.heuristics.prouning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.prouning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.prouning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking
import java.util.concurrent.TimeUnit

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
fun main(args: Array<String>) {
    val problem = Queens(8)
//    val solution = problem.solve(
//        strategy = Backtracking(
////            variableOrdering = SmallestDomainVariable(),
//            pruneSchema = ForwardChecking()
//        )
//    )
//    solution.print()
//    (solution as? Solved)?.let(::printQueens)

    val benchmark = Benchmark(problem, 3, TimeUnit.MINUTES, mapOf(
        "no prune" to Backtracking(),
        "FC      " to Backtracking(pruneSchema = ForwardChecking()),
        "PLA     " to Backtracking(pruneSchema = PartialLookAhead()),
        "FLA     " to Backtracking(pruneSchema = FullLookAhead())
    ))

    val results = benchmark.execute()

    results.entries.forEach { s, entry ->
        println("$s\t${entry.pretty(true)}")
    }
}