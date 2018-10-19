package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
sealed class Variable <A> {
    abstract fun remove(value: A): Variable<A>
    abstract fun removeAll(values: List<A>): Variable<A>

    companion object {
        fun <T> of(value: T) = Selected(value)
        fun <T> of(values: List<T>) = when (values.size) {
            1 -> of(values.first())
            else -> Choice(values)
        }
    }
}

data class Selected <A> (val value: A) : Variable<A>() {
    override fun remove(value: A) = this
    override fun removeAll(values: List<A>) = this
}

data class Choice <A> (val values: List<A>) : Variable<A>() {
    override fun remove(value: A) = of(values - value)
    override fun removeAll(values: List<A>) = of(this.values - values)
}