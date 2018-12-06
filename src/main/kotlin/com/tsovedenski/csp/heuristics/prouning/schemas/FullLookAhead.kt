package com.tsovedenski.csp.heuristics.prouning.schemas

import com.tsovedenski.csp.heuristics.prouning.PruneSchema
import com.tsovedenski.csp.heuristics.prouning.Slice
import com.tsovedenski.csp.toSet

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

class FullLookAhead<V>: PruneSchema<V> {
    override fun invoke(slice: Slice<V>): Set<V> = slice.current.toSet() + slice.next
}
