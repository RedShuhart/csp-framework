package com.tsovedenski.csp.wordsum

import com.tsovedenski.csp.benchmark.Benchmark
import com.tsovedenski.csp.heuristics.prouning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.prouning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.prouning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

/**
 * Created by Tsvetan Ovedenski on 13/10/18.
 *
 *
 *       S E N D
 *   +   M O R E
 *   = M O N E Y
 */
fun main(args: Array<String>) {
    val problem = WordSum("SEND", "MORE", "MONEY")

//    runSolution(problem)
    runBenchmark(problem)
}

fun runBenchmark(problem: WordSum) {
    val benchmark = Benchmark(problem, 1, 3, mapOf(
            "no prune" to Backtracking(),
            "FC"       to Backtracking(pruneSchema = ForwardChecking()),
            "PLA"      to Backtracking(pruneSchema = PartialLookAhead()),
            "FLA"      to Backtracking(pruneSchema = FullLookAhead())
    ))

    benchmark.execute().prettyPrint()
}

fun runSolution(problem: WordSum) {
    val solution = problem.solve(
            strategy = Backtracking(
//            variableOrdering = MostFrequentVariable(),
                    pruneSchema = FullLookAhead()
            )
    )
    solution.print()
}
