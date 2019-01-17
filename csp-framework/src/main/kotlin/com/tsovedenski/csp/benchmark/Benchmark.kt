package com.tsovedenski.csp.benchmark

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 10/12/18.
 */

/**
 * Benchmark different [strategies] for a given [solvable].
 *
 * @param solvable the problem to be solved.
 * @param runs how many times to solve the problem with one strategy.
 * @param warmupRuns how many times to solve the problem without recording statistics.
 * @param strategies a map of strategy identifier and the strategy itself.
 */
class Benchmark <V, D> (
    private val problem: Solvable<V, D>,
    private val runs: Int,
    private val warmupRuns: Int,
    private val strategies: Map<String, Strategy<V, D>>
) {

    fun execute() = BenchmarkSummary(
        strategies.mapValues { strategy ->
            runBenchmark(problem, strategy.value, runs, warmupRuns)
        }
    )
}

internal fun List<Statistics>.summarize(): Statistics {
    if (isEmpty()) {
        return Statistics.ZERO
    }
    val checks = foldRight(Pair(0L, 0)) { s, (c, i) -> Pair(c + s.counter, i + 1) }
    val time   = foldRight(Pair(0L, 0)) { s, (c, i) -> Pair(c + s.time, i + 1) }
    return Statistics(checks.first / checks.second, time.first / time.second)
}

internal fun <V, D> runBenchmark(
        problem: Solvable<V, D>,
        strategy: Strategy<V, D>,
        runs: Int,
        warmupRuns: Int
) = (1..runs + warmupRuns)
        .map { problem.solve(strategy) }
        .filterIsInstance<Solved<V, D>>()
        .map { it.statistics }
        .drop(warmupRuns)
        .summarize()