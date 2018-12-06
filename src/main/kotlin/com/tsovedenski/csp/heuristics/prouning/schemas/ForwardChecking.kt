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

class ForwardChecking<V, D>: PruneSchema<V, D> {
    override fun invoke(slice: Slice<V>, constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>>  {
        slice.next.firstOrNull()?.let { return selectConstraints(slice.current.toSet() + it.toSet(), constraints) }
        return selectConstraints(slice.current.toSet(), constraints)
    }
}
