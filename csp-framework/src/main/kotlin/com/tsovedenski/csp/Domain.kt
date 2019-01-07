package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */

/**
 * Representation of values.
 *
 * It can be either [Empty], [Selected] or [Choice].
 *
 * @param A type of the value.
 */
sealed class Domain <out A> {
    abstract fun toList(): List<A>
    companion object {
        fun <T> of(value: T): Domain<T> = Selected(value)
        fun <T> of(values: List<T>): Domain<T> = when (values.size) {
            0 -> Empty
            1 -> of(values.first())
            else -> Choice(values)
        }
        fun <T> of(vararg values: T) = of(values.toList())
    }
}

/**
 * No possible values.
 */
object Empty : Domain<Nothing>() {
    override fun toList(): List<Nothing> = emptyList()
}

/**
 * Single possible value.
 */
data class Selected <out A> (val value: A) : Domain<A>() {
    override fun toList(): List<A> = listOf(value)
}

/**
 * Many possible values.
 */
data class Choice <out A> (val values: List<A>) : Domain<A>() {
    override fun toList(): List<A> = values
}