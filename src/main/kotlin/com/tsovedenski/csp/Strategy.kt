package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
interface Strategy {
    /**
     * Take a job and return it possibly solved.
     */
    fun <V, D> run(job: Job<V, D>): Job<V, D>?
}