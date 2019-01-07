package com.tsovedenski.csp.heuristics.ordering.comparators

import com.tsovedenski.csp.Choice
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.heuristics.ordering.Comparison
import com.tsovedenski.csp.heuristics.ordering.Order

/**
 * Created by Ivan Yushchuk on 01/10/2018.
 */

/**
 * Choose the variable with smaller domain.
 */
class SmallestDomainVariable<V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>) =
            compareByDomainSize(a, b, Order.ASC)
}

/**
 * Choose the variable with bigger domain.
 */
class BiggestDomainVariable<V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>) =
            compareByDomainSize(a, b, Order.DESC)
}

internal fun <V, D> compareByDomainSize(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, order: Order): Comparison {
    val cmp = Comparison.compare(a.second.values.size, b.second.values.size, order)
    return when (cmp) {
        Comparison.EQ -> Comparison.GT
        else          -> cmp
    }
}

