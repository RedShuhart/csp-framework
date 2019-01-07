package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */

/**
 * Some description.
 *
 * @param V the type of variable identifier.
 * @param D the type of domain value.
 */
interface Strategy <V, D> {
    /**
     * Take a [job] and return it possibly solved.
     *
     * @return job with possibly complete [Job.assignment].
     */
    fun run(job: Job<V, D>): Job<V, D>?
}