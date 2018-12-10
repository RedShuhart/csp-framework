package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
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

object Empty : Domain<Nothing>() {
    override fun toList(): List<Nothing> = emptyList()
}

data class Selected <out A> (val value: A) : Domain<A>() {
    override fun toList(): List<A> = listOf(value)
}

data class Choice <out A> (val values: List<A>) : Domain<A>() {
    override fun toList(): List<A> = values
}