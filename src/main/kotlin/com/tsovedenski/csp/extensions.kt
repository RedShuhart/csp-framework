package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
internal fun <T> List<T>.pairs(): List<Pair<T, T>>
        = zip(1 .. size).flatMap { (a, i) -> drop(i).map { b -> a to b } }