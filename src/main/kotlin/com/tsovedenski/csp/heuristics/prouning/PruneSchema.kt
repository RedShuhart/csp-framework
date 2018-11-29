package com.tsovedenski.csp.heuristics.prouning

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

typealias PruneSchema <V> = (V, Set<V>, Set<V>) -> Set<V>

class DefaultPruneSchema<V> : PruneSchema<V> {
    override fun invoke(current: V, previous: Set<V>, next: Set<V>) = emptySet<V>()
}
