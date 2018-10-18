package com.tsovedenski.csp

import kotlin.math.min

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
internal fun <T> List<T>.pairs(): List<Pair<T, T>>
        = zip(1 .. size).flatMap { (a, i) -> drop(i).map { b -> a to b } }

internal fun <A, B> List<A>.pairs(other: List<B>): List<Pair<A, B>>
        = flatMap { a -> other.map { b -> a to b } }

internal fun <A, B> Sequence<A>.pairs(other: Sequence<B>): Sequence<Pair<A, B>>
        = flatMap { a -> other.map { b -> a to b } }

tailrec fun <T> fix(value: T, f: (T) -> T): T {
    val next = f(value)
    return when (next) {
        value -> next
        else  -> fix(next, f)
    }
}