package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
typealias Assignment <V, D> = MutableMap<V, Variable<D>>

fun <V, D> Assignment<V, D>.isComplete(): Boolean
        = all { it.value is Selected }

fun <V, D> Assignment<V, D>.print()
        = forEach { c, variable -> println("$c -> $variable") }
            .also { println("----------------------------") }

fun <V, D> emptyAssignment(): Assignment<V, D>
        = mutableMapOf()

fun <V, D> Assignment<V, D>.valueOf(key: V): D
        = (get(key) as Selected<D>).value