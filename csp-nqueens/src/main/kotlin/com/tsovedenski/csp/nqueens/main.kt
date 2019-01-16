package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.Assignment
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.benchmark.Benchmark
import com.tsovedenski.csp.benchmark.LazyBenchmark
import com.tsovedenski.csp.heuristics.ordering.comparators.MostFrequentVariable
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.heuristics.pruning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.reactor.ReactorBacktracking

import reactor.core.publisher.Mono
import reactor.core.publisher.TopicProcessor
import java.time.Duration

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
fun main(args: Array<String>) {
    // fun fact: if N is prime, there are less checks than if N is not prime
    val problem = Queens(15)
//    runSolution(problem)
//    runBenchmarks(problem)
//    runComparisons()

    repeat(15) {
        val n = it + 4
        val p = Queens(n)
        val b = Benchmark(p, 1, 1, mapOf(
            "n = $n" to Backtracking(pruneSchema = FullLookAhead(), variableOrdering = SmallestDomainVariable())
        ))
        b.execute().prettyPrint()
    }
}

fun runBenchmarks(problem: Queens) {
    val benchmark = LazyBenchmark(problem, 1, 3, mapOf(
        "no prune" to Backtracking(),
        "FC"       to Backtracking(pruneSchema = ForwardChecking()),
        "PLA"      to Backtracking(pruneSchema = PartialLookAhead()),
        "FLA"      to Backtracking(pruneSchema = FullLookAhead())
    ))

    println("strategy,counter,time[ms]")
    benchmark.execute().forEach { println("${it.first},${it.second.csv()}") }
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
//            .doOnNext{ printQueensPartial(it)}
            .doOnComplete{
                print("Finished")
                processor.shutdown()
            }.subscribe()

    val solution = problem.solve(
        strategy = Backtracking(
            pruneSchema = FullLookAhead(),
            variableOrdering = SmallestDomainVariable()
        )
    )
    solution.print()
    (solution as? Solved)?.let(::printQueens)
}
