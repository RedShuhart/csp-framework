package com.tsovedenski.csp.benchmark

import com.tsovedenski.csp.*
import java.util.concurrent.TimeUnit

/**
 * Created by Tsvetan Ovedenski on 10/12/18.
 */
class Benchmark <V, D> (
    val solvable: Solvable<V, D>,
    val runs: Int,
    val timeout: TimeUnit,
    val strategies: Map<String, Strategy<V, D>>
) {

    fun execute(): Summary {
        return Summary(
            strategies.mapValues { strategy ->
                (1..runs)
                        .map { solvable.solve(strategy.value) }
                        .filterIsInstance<Solved<V, D>>()
                        .map { it.statistics }
                        .summarize()
            }
        )
    }

    private fun List<Statistics>.summarize(): Statistics {
        val checks = foldRight(Pair(0L, 0)) { s, (c, i) -> Pair(c + s.counter, i + 1) }
        val time   = foldRight(Pair(0L, 0)) { s, (c, i) -> Pair(c + s.time, i + 1) }
        return Statistics(checks.first / checks.second, time.first / time.second)
    }
}