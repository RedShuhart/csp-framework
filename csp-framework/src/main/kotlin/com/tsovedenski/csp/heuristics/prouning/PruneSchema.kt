package com.tsovedenski.csp.heuristics.prouning

import com.tsovedenski.csp.BinaryConstraint

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 *
 */

enum class Direction {
    SINGLE, BOTH
}


//typealias PruneSchema <V, D> = (Slice<V>, List<BinaryConstraint<V, D>>) -> List<BinaryConstraint<V, D>>

abstract class PruneSchema <V, D> (val direction: Direction) {
    abstract operator fun invoke(slice: Slice<V>,  constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>>
}

class DefaultPruneSchema <V, D> : PruneSchema<V, D>(direction = Direction.SINGLE) {
    override fun invoke(slice: Slice<V>,  constraints: List<BinaryConstraint<V, D>>) = emptyList<BinaryConstraint<V, D>>()
}

fun <V, D> selectConstraints(variablesToPrune: Set<V>, constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>> {
    val prunedConstraints = constraints.filter { it.variables[0] in variablesToPrune }.groupBy { it.variables[0] }
    return variablesToPrune.flatMap { prunedConstraints[it] ?: emptyList() }
}
