package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 14/10/2018.
 */
sealed class Variable <A>

data class Selected <A> (val value: A) : Variable<A>()

data class Choice <A> (val values: List<A>) : Variable<A>()