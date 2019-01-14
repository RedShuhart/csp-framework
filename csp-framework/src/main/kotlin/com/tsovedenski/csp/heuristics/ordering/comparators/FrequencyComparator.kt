package com.tsovedenski.csp.heuristics.ordering.comparators

import com.tsovedenski.csp.Choice
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.heuristics.ordering.Comparison
import com.tsovedenski.csp.heuristics.ordering.Order

/**
 * Created by Ivan Yushchuk on 01/10/2018.
 */

/**
 * Choose the variable which is more used in constraints.
 */
class MostFrequentVariable<V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>) =
            compareByFrequency(a, b, c, Order.DESC)
}

/**
 * Choose the variable which is less used in constraints.
 */
class LeastFrequentVariable<V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>) =
            compareByFrequency(a, b, c, Order.ASC)
}

internal fun <V, D> compareByFrequency(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>, order: Order): Comparison {
    val aOccurrences = c.map { it.variables }.filter { a.first in it }.size
    val bOccurrences = c.map { it.variables }.filter { b.first in it }.size
    val cmp = Comparison.compare(aOccurrences, bOccurrences, order)
    return when (cmp) {
        Comparison.EQ -> Comparison.GT
        else          -> cmp
    }
}
