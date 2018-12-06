package com.tsovedenski.csp.heuristics.prouning.schemas

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.heuristics.prouning.PruneSchema
import com.tsovedenski.csp.heuristics.prouning.Slice
import com.tsovedenski.csp.heuristics.prouning.selectConstraints
import com.tsovedenski.csp.toSet

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

class PartialLookAhead<V, D>: PruneSchema<V, D> {
    override fun invoke(slice: Slice<V>, constraints: List<BinaryConstraint<V, D>>)
            = selectConstraints(slice.current.toSet() + slice.next, constraints)
}
