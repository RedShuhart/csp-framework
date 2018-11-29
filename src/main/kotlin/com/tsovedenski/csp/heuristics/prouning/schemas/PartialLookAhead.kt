package com.tsovedenski.csp.heuristics.prouning.schemas

import com.tsovedenski.csp.heuristics.prouning.PruneSchema

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

class PartialLookAhead<V>: PruneSchema<V> {
    override fun invoke(current: V, previuos: Set<V>, next: Set<V>): Set<V> = next + current
}
