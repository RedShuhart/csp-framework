package com.tsovedenski.csp.heuristics.ordering.comparators

import com.tsovedenski.csp.Choice
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.heuristics.ordering.Comparison

/**
 * Created by Ivan Yushchuk on 01/10/2018.
 *
 */

typealias VariableComparator <V, D> = (Pair<V, Choice<D>>, Pair<V, Choice<D>>, List<Constraint<V, D>>) -> Comparison

class DefaultComparator <V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>) = Comparison.GT
}
