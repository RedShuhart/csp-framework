package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 *
 * (Slow) Generate-and-Test algorithm
 */
enum class Ordering (val asInt: Int) {
    GT(1), LT(-1), EQ(0);
    companion object {
        fun fromInts(a: Int, b: Int)
                = when {
                    a < b -> LT
                    a > b -> GT
                    else  -> EQ
                }
    }
}
typealias VariableComparator <V, D> = (Pair<V, Choice<D>>, Pair<V, Choice<D>>, List<Constraint<V,D>>) -> Ordering

class DefaultComparator <V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>) = Ordering.EQ
}

class MostFamousVariable <V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>): Ordering {
        val aOccurences = c.map { it.variables }.filter { a.first in it }.size
        val bOccurences = c.map { it.variables }.filter { b.first in it }.size
        return Ordering.fromInts(aOccurences, bOccurences)
    }
}

class SmallestDomainVariable <V, D> : VariableComparator<V, D> {
    override fun invoke(a: Pair<V, Choice<D>>, b: Pair<V, Choice<D>>, c: List<Constraint<V, D>>): Ordering {
        return Ordering.fromInts(a.second.values.size, b.second.values.size)
    }
}

class Backtracking <V, D> (
    private val variableOrdering: VariableComparator<V, D> = DefaultComparator()
) : Strategy<V, D> {

    override fun run(job: Job<V, D>): Job<V, D>? {
        if (job.isComplete() && job.isValid()) return job

        job.step()

        val variable = job.selectUnassignedVariable(variableOrdering) ?: return null
        variable.value.values.forEach {
            val attempt = Selected(it)
            job.assignVariable(variable.key, attempt)
            if (job.isPartiallyValid()) {
                val result = run(job)
                if (result != null) {
                    return result
                }
            }
            job.assignVariable(variable.key, variable.value)
        }

        return null
    }

}

//(a, b), constraints -> GT LE