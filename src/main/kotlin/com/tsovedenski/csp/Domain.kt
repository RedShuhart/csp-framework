package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
sealed class Domain <out A> {
    companion object {
        fun <T> of(value: T): Domain<T> = Selected(value)
        fun <T> of(values: List<T>): Domain<T> = when (values.size) {
            0 -> Empty
            1 -> of(values.first())
            else -> Choice(values)
        }
    }
}

object Empty : Domain<Nothing>()

data class Selected <out A> (val value: A) : Domain<A>()

data class Choice <out A> (val values: List<A>) : Domain<A>()