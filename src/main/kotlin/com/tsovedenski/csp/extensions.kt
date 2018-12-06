@file:Suppress("UNCHECKED_CAST")

package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
internal inline fun <K, reified R> Map<K, *>.filterIsInstance(): Map<K, R>
    = filterValues { it is R } as Map<K, R>

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
internal inline fun <A, B, T> Pair<A, B>.mapLeft(crossinline f: (A) -> T): Pair<T, B>
        = Pair(f(first), second)

internal inline fun <A, B, T> Pair<A, B>.mapRight(crossinline f: (B) -> T): Pair<A, T>
        = Pair(first, f(second))

internal inline fun <A, B, X, Y> Pair<A, B>.map(crossinline left: (A) -> X, crossinline right: (B) -> Y): Pair<X, Y>
        = Pair(left(first), right(second))

// Variables to domain map
fun <V, D> List<V>.withDomain(domain: List<D>): Map<V, List<D>> = associate { it to domain }

fun <V> V?.toSet(): Set<V> = if(this == null) emptySet() else setOf(this)

// fix
internal tailrec fun <T> fix(value: T, f: (T) -> T): T {
    val next = f(value)
    return when (next) {
        value -> next
        else  -> fix(next, f)
    }
}
