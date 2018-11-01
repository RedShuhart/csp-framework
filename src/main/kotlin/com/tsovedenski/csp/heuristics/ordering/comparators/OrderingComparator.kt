package com.tsovedenski.csp.heuristics.ordering.comparators

import com.tsovedenski.csp.Choice
import com.tsovedenski.csp.Constraint

/**
 * Created by Ivan Yushchuk on 01/10/2018.
 *
 */

class OrderingComparator<V, D>(
        private val ordering: VariableComparator<V, D>,
        private val constraints: List<Constraint<V, D>>) : Comparator<Map.Entry<V, Choice<D>>> {

    override fun compare(o1: Map.Entry<V, Choice<D>>, o2: Map.Entry<V, Choice<D>>) =
            ordering(o1.key to o1.value, o2.key to o2.value, constraints).asInt
}
