package com.tsovedenski.csp.heuristics.prouning.schemas

import com.tsovedenski.csp.heuristics.prouning.PruneSchema

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

class FullLookAhead<V>: PruneSchema<V> {
    override fun invoke(current: V, previuos: Set<V>, next: Set<V>): Set<V> = previuos.union(next).plus(current)
}
