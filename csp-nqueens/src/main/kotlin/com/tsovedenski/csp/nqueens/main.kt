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
//    runSolution()
    runBenchmarks()
}

fun runBenchmarks() {
    val problem = Queens(7)
    val benchmark = Benchmark(problem, 1, 3, mapOf(
            "no prune" to Backtracking(),
            "FC"       to Backtracking(pruneSchema = ForwardChecking()),
            "PLA"      to Backtracking(pruneSchema = PartialLookAhead()),
            "FLA"      to Backtracking(pruneSchema = FullLookAhead())
    ))

    benchmark.execute().prettyPrint()
}

fun runSolution() {
    val problem = Queens(7)
    val solution = problem.solve(
        strategy = Backtracking(
//            variableOrdering = SmallestDomainVariable(),
            pruneSchema = ForwardChecking()
        )
    )
    solution.print()
    (solution as? Solved)?.let(::printQueens)
}