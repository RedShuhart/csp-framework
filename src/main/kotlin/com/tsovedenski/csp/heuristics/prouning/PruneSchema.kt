package com.tsovedenski.csp.heuristics.prouning

import com.tsovedenski.csp.AllDiffConstraint
import com.tsovedenski.csp.BinaryConstraint

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

typealias PruneSchema <V, D> = (Slice<V>, List<BinaryConstraint<V, D>>) -> List<BinaryConstraint<V, D>>

class DefaultPruneSchema<V, D> : PruneSchema<V, D> {
    override fun invoke(slice: Slice<V>,  constraints: List<BinaryConstraint<V, D>>)= emptyList<BinaryConstraint<V, D>>()
}

fun <V, D> selectConstraints(variablesToPrune: Set<V>, constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>> {
    val prunedConstraints = constraints.filter { it.variables[0] in variablesToPrune }.groupBy { it.variables[0] }
    return variablesToPrune.flatMap { prunedConstraints.getValue(it) }
}
