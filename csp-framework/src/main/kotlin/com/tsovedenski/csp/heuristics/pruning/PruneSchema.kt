package com.tsovedenski.csp.heuristics.pruning

import com.tsovedenski.csp.BinaryConstraint

/**
 * Created by Ivan Yushchuk on 28/11/2018.
 */

/**
 * Whether to prune in [SINGLE] direction or in [BOTH] directions.
 */
enum class Direction {
    SINGLE, BOTH
}

//typealias PruneSchema <V, D> = (Slice<V>, List<BinaryConstraint<V, D>>) -> List<BinaryConstraint<V, D>>

/**
 * Schema that decides domains [com.tsovedenski.csp.Domain] of which variables to prune and selects constraints [com.tsovedenski.csp.Constraint] they are involved in.
 *
 * @param direction see [Direction].
 */
abstract class PruneSchema <V, D> (val direction: Direction) {
    /**
     * Select relevant [BinaryConstraint]s to [Slice].
     */
    abstract operator fun invoke(slice: Slice<V>,  constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>>
}

/**
 * This schema returns empty list of constraints [com.tsovedenski.csp.Constraint], i.e. it never prunes.
 */
class DefaultPruneSchema <V, D> : PruneSchema<V, D> (direction = Direction.SINGLE) {
    override fun invoke(slice: Slice<V>,  constraints: List<BinaryConstraint<V, D>>) = emptyList<BinaryConstraint<V, D>>()
}

internal fun <V, D> selectConstraints(variablesToPrune: Set<V>, constraints: List<BinaryConstraint<V, D>>): List<BinaryConstraint<V, D>> {
    val prunedConstraints = constraints.filter { it.variables[0] in variablesToPrune }.groupBy { it.variables[0] }
    return variablesToPrune.flatMap { prunedConstraints[it] ?: emptyList() }
}
