package com.tsovedenski.csp

import kotlin.math.min

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
// pairs
internal fun <T> List<T>.pairs(): List<Pair<T, T>>
        = zip(1 .. size).flatMap { (a, i) -> drop(i).map { b -> a to b } }

internal fun <A, B> List<A>.pairs(other: List<B>): List<Pair<A, B>>
        = flatMap { a -> other.map { b -> a to b } }

internal fun <T> Sequence<T>.pairs(): Sequence<Pair<T, T>>
        = zip((1..count()).asSequence()).flatMap { (a, i) -> drop(i).map { b -> a to b } }

internal fun <A, B> Sequence<A>.pairs(other: Sequence<B>): Sequence<Pair<A, B>>
        = flatMap { a -> other.map { b -> a to b } }

// Pair map
inline fun <A, B, T> Pair<A, B>.mapLeft(crossinline f: (A) -> T): Pair<T, B>
        = Pair(f(first), second)

inline fun <A, B, T> Pair<A, B>.mapRight(crossinline f: (B) -> T): Pair<A, T>
        = Pair(first, f(second))

inline fun <A, B, X, Y> Pair<A, B>.map(crossinline left: (A) -> X, crossinline right: (B) -> Y): Pair<X, Y>
        = Pair(left(first), right(second))

// Variables to domain map
fun <V, D> List<V>.toDomain(domain: List<D>): Map<V, List<D>> = associate { it to domain }

// fix
tailrec fun <T> fix(value: T, f: (T) -> T): T {
    val next = f(value)
    return when (next) {
        value -> next
        else  -> fix(next, f)
    }
}
