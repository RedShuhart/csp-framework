package com.tsovedenski.csp

import kotlin.coroutines.experimental.*

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
//typealias Constraint <V, D> = (Assignment<V, D>) -> Boolean
interface Constraint <V, D> : (Assignment<V, D>) -> Boolean {
    val variables: List<V>
}

fun <V, D> Constraint<V, D>.canCheck(assignment: Assignment<V, D>)
        = variables.map(assignment::getValue).filterIsInstance<Selected<D>>().size == variables.size

data class UnaryConstraint <V, D> (val variable: V, val f: (D) -> Boolean) : Constraint<V, D> {
    override val variables = listOf(variable)

    override fun invoke(map: Assignment<V, D>): Boolean {
        val value = map.valueOf(variable)
        return f(value)
    }
}

data class BinaryConstraint <V, D> (val a: V, val b: V, val f: (D, D) -> Boolean) : Constraint<V, D> {
    override val variables = listOf(a, b)

    override fun invoke(map: Assignment<V, D>): Boolean {
        val valueA = map.valueOf(a)
        val valueB = map.valueOf(b)
        return f(valueA, valueB)
    }
}

class AllDiffConstraint <V, D> (override val variables: List<V>) : Constraint<V, D> {
    constructor(vararg variables: V): this(variables.toList())

    private val list = variables.pairs().map { (a, b) ->
        BinaryConstraint(a, b, ::neq)
    }

    override fun invoke(map: Assignment<V, D>): Boolean {
        return list.all { it(map) }
    }

//    override fun invoke(map: Assignment<V, D>): Boolean {
//        val values = vars.map(map::getValue)
//        return values.distinct().size == values.size
//    }

    private fun neq(x: D, y: D) = x != y
}

// TODO: Can we do this with coroutines?
// the problem is: `getValue` returns D, but `body` returns a Boolean...
class GeneralConstraint <V, D> (
    override val variables: List<V>,
    val body: GeneralConstraint<V, D>.ConstraintContext.() -> Boolean
) : Constraint<V, D> {
    inner class ConstraintContext (private val assignment: Assignment<V, D>) {
        fun getValue(key: V): D = assignment.valueOf(key)
    }

    override fun invoke(map: Assignment<V, D>): Boolean
            = ConstraintContext(map).body()
}