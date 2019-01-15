package com.tsovedenski.csp.benchmark

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 15/01/19.
 */
class LazyBenchmark <V, D> (
    private val problem: Solvable<V, D>,
    private val runs: Int,
    private val warmupRuns: Int,
    private val strategies: Map<String, Strategy<V, D>>
) {
    fun execute(): Sequence<Pair<String, Statistics>> = sequence {
        for (name in strategies.keys) {
            val strategy = strategies.getValue(name)
            val result = runBenchmark(problem, strategy, runs, warmupRuns)
            yield(name to result)
        }
    }
}