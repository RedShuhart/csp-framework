package com.tsovedenski.csp.heuristics.prouning.schemas

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.heuristics.prouning.Direction
import com.tsovedenski.csp.heuristics.prouning.PruneSchema
import com.tsovedenski.csp.heuristics.prouning.Slice
import com.tsovedenski.csp.heuristics.prouning.selectConstraints
import com.tsovedenski.csp.toSet

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

class ForwardChecking<V, D>: PruneSchema<V, D>(direction = Direction.BOTH) {
    override fun invoke(slice: Slice<V>, constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>>  {
        slice.current ?: return emptyList()
        val nextVariable = slice.next.firstOrNull() ?: return emptyList()
        val constraintsWithNext = constraints.filter { it.variables.contains(nextVariable) }
        return selectConstraints(setOf(slice.current, nextVariable), constraintsWithNext) }
    }
