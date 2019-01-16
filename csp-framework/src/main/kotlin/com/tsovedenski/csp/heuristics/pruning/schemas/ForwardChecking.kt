package com.tsovedenski.csp.heuristics.pruning.schemas

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.heuristics.pruning.Direction
import com.tsovedenski.csp.heuristics.pruning.PruneSchema
import com.tsovedenski.csp.heuristics.pruning.Slice
import com.tsovedenski.csp.heuristics.pruning.selectConstraints

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 */

/**
 * Forward checking pruning schema.
 *
 * It selects constraints whose variables are current and the very next one.
 * Pruning direction is set to [Direction.SINGLE]
 */
class ForwardChecking<V, D>: PruneSchema<V, D>(direction = Direction.SINGLE) {
    override fun invoke(slice: Slice<V>, constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>>  {
        slice.current ?: return emptyList()
        val nextVariable = slice.next.firstOrNull() ?: return emptyList()
        val constraintsWithNext = constraints.filter { it.variables[1] == nextVariable }
        return selectConstraints(slice.previous + setOf(slice.current, nextVariable), constraintsWithNext)
    }
}
