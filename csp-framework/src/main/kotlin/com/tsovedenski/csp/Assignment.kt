package com.tsovedenski.csp

import com.tsovedenski.csp.heuristics.prouning.Direction

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

fun <V, D> Assignment<V, D>.variablesOf(key: V): List<D>
    = get(key)?.toList() ?: emptyList()

fun <V, D> Assignment<V, D>.consistentWith(constraints: List<Constraint<V, D>>, direction: Direction = Direction.SINGLE): Assignment<V, D> = fix(this) { a ->
    var next = a
    next = constraints.filterIsInstance<UnaryConstraint<V, D>>().fold(next) { m, c -> m.nodeConsistent(c) }
    next = constraints.filterIsInstance<BinaryConstraint<V, D>>().fold(next) { m, c -> m.arcConsistent(c, direction) }
    next = constraints.filterIsInstance<AllDiffConstraint<V, D>>().fold(next) { m, c -> m.arcConsistent(c, direction) }
    next
}

fun <V, D> Assignment<V, D>.nodeConsistent(constraint: UnaryConstraint<V, D>): Assignment<V, D> {
    val values = (get(constraint.variable) as? Choice)?.values ?: return this
    val filtered = values.filter { constraint.f(it) }

    val copy = toMutableMap()
    copy[constraint.variable] = Domain.of(filtered)

    return copy
}

fun <V, D> Assignment<V, D>.arcConsistent(constraint: BinaryConstraint<V, D>, direction: Direction): Assignment<V, D> {
    val copy = toMutableMap()

    fun go(varA: V, varB: V, reversed: Boolean) {
        val a = get(varA) ?: return
        val b = get(varB)?.toList() ?: return

        val filtered = when (a) {
            is Empty    -> emptyList()
            is Selected -> b.filter { if (reversed) constraint.f(it, a.value) else constraint.f(a.value, it) }
            is Choice   -> b.pairs(a.values)
                    .asSequence()
                    .filter { (b, a) -> if (reversed) constraint.f(b, a) else constraint.f(a, b) }
                    .map(Pair<D, D>::first)
                    .distinct()
                    .toList()
        }

        copy[varB] = Domain.of(filtered)
    }

    when(direction) {
        Direction.SINGLE -> {
            go(constraint.a, constraint.b, reversed = false)
        }
        Direction.BOTH -> {
            go(constraint.a, constraint.b, reversed = false)
            go(constraint.b, constraint.a, reversed = true)
        }
    }

    return copy
}

fun <V, D> Assignment<V, D>.arcConsistent(constraint: AllDiffConstraint<V, D>, direction: Direction): Assignment<V, D>
        = constraint.asBinaryConstraints().fold(this) { m, c -> m.arcConsistent(c, direction) }
