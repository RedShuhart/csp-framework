package com.tsovedenski.csp.heuristics.prouning

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

typealias PruneSchema <V> = (Slice<V>) -> Set<V>

class DefaultPruneSchema<V> : PruneSchema<V> {
    override fun invoke(slice: Slice<V>) = emptySet<V>()
}
