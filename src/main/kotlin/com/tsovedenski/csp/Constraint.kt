package com.tsovedenski.csp

import kotlin.coroutines.experimental.*

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
typealias Constraint <V, D> = (Assignment<V, D>) -> Boolean

data class UnaryConstraint <V, D> (val variable: V, val f: (D) -> Boolean) : Constraint<V, D> {
    override fun invoke(map: Assignment<V, D>): Boolean {
        val value = map.valueOf(variable) ?: return false
        return f(value)
    }
}

data class BinaryConstraint <V, D> (val a: V, val b: V, val f: (D, D) -> Boolean) : Constraint<V, D> {
    override fun invoke(map: Assignment<V, D>): Boolean {
        val valueA = map.valueOf(a) ?: return false
        val valueB = map.valueOf(b) ?: return false
        return f(valueA, valueB)
    }
}

class AllDiffConstraint <V, D> (val vars: List<V>) : Constraint<V, D> {
    constructor(vararg vars: V): this(vars.toList())

    override fun invoke(map: Assignment<V, D>): Boolean {
        val list = vars.zip(vars.drop(1)) { a, b ->
            BinaryConstraint(a, b, ::neq)
        }
        return list.all { it(map) }
    }

    private fun neq(x: D, y: D) = x != y
}

// TODO: Can we do this with coroutines?
// the problem is: `getValue` returns D, but `body` returns a Boolean...
class GeneralConstraint <V, D> (
    val body: GeneralConstraint<V, D>.ConstraintContext.() -> Boolean
) : Constraint<V, D> {
    inner class ConstraintContext (private val assignment: Assignment<V, D>) {

        fun getValue(key: V): D {
            val value = assignment.valueOf(key) ?: throw NotSelectedException
            return value
        }

    }

    override fun invoke(map: Assignment<V, D>): Boolean {
        return try {
            val context = ConstraintContext(map)
            context.body()
        } catch (e: Exception) {
            when (e) {
                NotSelectedException -> false
                else -> throw e
            }
        }
    }
}

internal object NotSelectedException : Exception()