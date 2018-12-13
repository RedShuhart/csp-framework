package com.tsovedenski.csp.benchmark

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 10/12/18.
 */
class Benchmark <V, D> (
    private val solvable: Solvable<V, D>,
    private val runs: Int,
    private val warmupRuns: Int,
    private val strategies: Map<String, Strategy<V, D>>
) {

    fun execute() = BenchmarkSummary(
        strategies.mapValues { strategy ->
            (1..runs + warmupRuns)
                    .map { solvable.solve(strategy.value) }
                    .filterIsInstance<Solved<V, D>>()
                    .map { it.statistics }
                    .drop(warmupRuns)
                    .summarize()
        }
    )

    private fun List<Statistics>.summarize(): Statistics {
        val checks = foldRight(Pair(0L, 0)) { s, (c, i) -> Pair(c + s.counter, i + 1) }
        val time   = foldRight(Pair(0L, 0)) { s, (c, i) -> Pair(c + s.time, i + 1) }
        return Statistics(checks.first / checks.second, time.first / time.second)
    }
}