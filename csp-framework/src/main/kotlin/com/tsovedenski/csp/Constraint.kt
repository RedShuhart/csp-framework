package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * A function from some type [A] to [Boolean].
 */
typealias Predicate <A> = (A) -> Boolean

/**
 * A function from a pair of [A] to [Boolean].
 */
typealias BiPredicate <A> = (A, A) -> Boolean

typealias Indexed <T> = Pair<Int, T>

/**
 * A constraint is a function from [Assignment] to a [Boolean].
 * @property variables variables that take part in this constraint.
 */
interface Constraint <V, D> : (Assignment<V, D>) -> Boolean {
    val variables: List<V>
}

/**
 * An [assignment] can be checked only if all of [Constraint.variables] are [Selected].
 */
fun <V, D> Constraint<V, D>.canCheck(assignment: Assignment<V, D>)
        = variables.asSequence().map(assignment::getValue).all { it is Selected }

/**
 * A constraint with a single variable.
 * @param variable constrained variable.
 * @param f: predicate for [variable].
 */
data class UnaryConstraint <V, D> (
    val variable: V,
    val f: Predicate<D>
) : Constraint<V, D> {
    override val variables = listOf(variable)

    override fun invoke(map: Assignment<V, D>): Boolean {
        val value = map.valueOf(variable)
        return f(value)
    }
}

/**
 * Allows converting various constraints to [BinaryConstraint].
 * @see [com.tsovedenski.csp.heuristics.pruning.PruneSchema].
 */
interface AsBinaryConstraints <V, D> {
    fun asBinaryConstraints(): List<BinaryConstraint<V, D>>
}

/**
 * A constraint between two variables.
 * @param a first variable.
 * @param b second variable.
 * @param f [BiPredicate] for [a] and [b].
 */
data class BinaryConstraint <V, D> (
    val a: V,
    val b: V,
    val f: BiPredicate<D>
) : Constraint<V, D>, AsBinaryConstraints<V, D> {
    override val variables = listOf(a, b)

    override fun invoke(map: Assignment<V, D>): Boolean {
        val valueA = map.valueOf(a)
        val valueB = map.valueOf(b)
        return f(valueA, valueB)
    }

    override fun asBinaryConstraints() = listOf(this)
}

/**
 * Alldiff constraint in which all [variables] should be different.
 */
class AllDiffConstraint <V, D> (
    override val variables: List<V>
) : Constraint<V, D>, AsBinaryConstraints<V, D> {
    constructor(vararg variables: V): this(variables.toList())

    private val list = variables.pairs().map { (a, b) ->
        BinaryConstraint<V, D>(a, b, ::neq)
    }

    override fun invoke(map: Assignment<V, D>): Boolean {
        return list.all { it(map) }
    }

    override fun asBinaryConstraints() = list.toList()

    companion object {
        private fun <T> neq(x: T, y: T) = x != y
    }
}

/**
 * Alldiff constraint in which all [variables] should be different.
 *
 * Gives access to index of a variable.
 *
 * @see [AllDiffConstraint]
 */
class AllIndexedConstraint <V, D> (
    override val variables: List<V>,
    f: BiPredicate<Indexed<D>>
) : Constraint<V, D>, AsBinaryConstraints<V, D> {
    constructor(vararg variables: V, f: BiPredicate<Indexed<D>>): this(variables.toList(), f)

    private val list = indexes.zip(variables.asSequence()).pairs().map { (a, b) ->
        BinaryConstraint<V, D>(a.second, b.second) { x, y -> f(a.mapRight { x }, b.mapRight { y }) }
    }

    override fun invoke(map: Assignment<V, D>): Boolean {
        return list.all { it(map) }
    }

    override fun asBinaryConstraints() = list.toList()

    companion object {
        private val indexes = generateSequence(0) { it + 1 }
    }
}

// TODO: Can we do this with coroutines?
// the problem is: `getValue` returns D, but `body` returns a Boolean...

/**
 * An arbitrary constraint.
 */
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
