package com.tsovedenski.csp.heuristics.pruning.schemas

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.heuristics.pruning.Direction
import com.tsovedenski.csp.heuristics.pruning.PruneSchema
import com.tsovedenski.csp.heuristics.pruning.Slice
import com.tsovedenski.csp.heuristics.pruning.selectConstraints
import com.tsovedenski.csp.toSet

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 */

/**
 * Full Look-Ahead pruning schema.
 *
 * It selects constraints whose variables are... TODO
 */
class FullLookAhead<V, D>: PruneSchema<V, D>(direction = Direction.BOTH) {
    override fun invoke(slice: Slice<V>, constraints: List<BinaryConstraint<V, D>>)
            = selectConstraints(slice.current.toSet() + slice.next, constraints)
}
