package com.tsovedenski.csp

import com.tsovedenski.csp.strategies.Backtracking

/**
 * Created by Tsvetan Ovedenski on 14/11/2018.
 */
interface Solvable <V, D> {
    fun toTask(): Task<V, D>
}

fun <V, D> Solvable<V, D>.solve(strategy: Strategy<V, D> = Backtracking<V, D>())
        = toTask().solve(strategy)