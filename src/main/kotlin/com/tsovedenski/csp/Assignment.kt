package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
typealias Assignment <V, D> = MutableMap<V, Domain<D>>

fun <V, D> Assignment<V, D>.isComplete(): Boolean
        = all { it.value is Selected }

fun <V, D> Assignment<V, D>.print()
        = forEach { c, variable -> println("$c -> $variable") }
            .also { println("----------------------------") }

fun <V, D> emptyAssignment(): Assignment<V, D>
        = mutableMapOf()

fun <V, D> Assignment<V, D>.valueOf(key: V): D
        = (get(key) as Selected<D>).value

fun <V, D> Assignment<V, D>.consistentWith(constraints: List<Constraint<V, D>>): Assignment<V, D> = fix(this) { a ->
    var next = a
    next = constraints.filterIsInstance<UnaryConstraint<V, D>>().fold(next) { m, c -> m.nodeConsistent(c) }
    next = constraints.filterIsInstance<BinaryConstraint<V, D>>().fold(next) { m, c -> m.arcConsistent(c) }
    next = constraints.filterIsInstance<AllDiffConstraint<V, D>>().fold(next) { m, c -> m.arcConsistent(c) }
    next
}

fun <V, D> Assignment<V, D>.nodeConsistent(constraint: UnaryConstraint<V, D>): Assignment<V, D> {
    val values = (getValue(constraint.variable) as? Choice)?.values ?: return this
    val filtered = values.filter { constraint.f(it) }

    val copy = toMutableMap()
    copy[constraint.variable] = Domain.of(filtered)

    return copy
}

fun <V, D> Assignment<V, D>.arcConsistent(constraint: BinaryConstraint<V, D>): Assignment<V, D> {
    val a = (getValue(constraint.a) as? Choice)?.values ?: return this

    val b = getValue(constraint.b)

    val filtered = when (b) {
        is Empty    -> a
        is Selected -> a.filter { constraint.f(it, b.value) }
        is Choice   -> a.pairs(b.values)
                .asSequence()
                .filter { (a, b) -> constraint.f(a, b) }
                .map(Pair<D, D>::first)
                .distinct()
                .toList()
    }

    val copy = toMutableMap()
    copy[constraint.a] = Domain.of(filtered)

    return copy
}

fun <V, D> Assignment<V, D>.arcConsistent(constraint: AllDiffConstraint<V, D>): Assignment<V, D>
        = constraint.asBinaryConstraints().fold(this) { m, c -> m.arcConsistent(c) }