package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
interface Strategy <V, D> {
    /**
     * Take a job and return it possibly solved.
     */
    fun run(job: Job<V, D>): Job<V, D>?
}