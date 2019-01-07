package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.benchmark.Benchmark
import com.tsovedenski.csp.heuristics.pruning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
fun main(args: Array<String>) {
    // fun fact: if N is prime, there are less checks than if N is not prime
    val problem = Queens(7)
//    runSolution(problem)
//    runBenchmarks(problem)
    runComparisons()
}

fun runBenchmarks(problem: Queens) {
    val benchmark = Benchmark(problem, 1, 3, mapOf(
            "no prune" to Backtracking(),
            "FC"       to Backtracking(pruneSchema = ForwardChecking()),
            "PLA"      to Backtracking(pruneSchema = PartialLookAhead()),
            "FLA"      to Backtracking(pruneSchema = FullLookAhead())
    ))

    benchmark.execute().prettyPrint()
}

fun runComparisons() {
    repeat(18) {
        val p = Queens(it)
        val s = p.solve(Backtracking(pruneSchema = FullLookAhead()))
        print("n = $it  ")
        if (s is Solved) {
            println(s.statistics.pretty(true))
        } else {
            println("No solution")
        }
    }
}

fun runSolution(problem: Queens) {
    val solution = problem.solve(
        strategy = Backtracking(
            pruneSchema = FullLookAhead()
        )
    )
    solution.print()
    (solution as? Solved)?.let(::printQueens)
}
