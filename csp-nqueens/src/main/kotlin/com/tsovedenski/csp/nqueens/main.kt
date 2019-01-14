package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.Assignment
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.benchmark.Benchmark
import com.tsovedenski.csp.heuristics.pruning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.reactor.Backtracking as ReactorBacktrack

import reactor.core.publisher.Mono
import reactor.core.publisher.TopicProcessor
import java.lang.AssertionError
import java.time.Duration
import javax.annotation.processing.Processor

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
fun main(args: Array<String>) {
    // fun fact: if N is prime, there are less checks than if N is not prime
    val problem = Queens(7)
    runSolution(problem)
//    runBenchmarks(problem)
//    runComparisons()
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

    val processor = TopicProcessor.create<Assignment<Int, Int>>()
    val sink = processor.sink()

    processor.concatMap { it -> Mono.just(it).delayElement(Duration.ofMillis(100)) }
            .doOnNext{ printQueensPartial(it)}
            .doOnComplete{
                print("Finished")
                processor.shutdown()
            }.subscribe()

    val solution = problem.solve(
        strategy = ReactorBacktrack(
            pruneSchema = ForwardChecking(),
                sink = sink
        )
    )
    solution.print()
    (solution as? Solved)?.let(::printQueens)
}
