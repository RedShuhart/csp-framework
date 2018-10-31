package com.tsovedenski.csp.strategies

import com.tsovedenski.csp.Choice
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Strategy

/**
 * Created by Tsvetan Ovedenski on 17/10/18.
 */
//object ForwardChecking : Strategy {
//    override fun <V, D> run(job: Job<V, D>): Job<V, D>? {
//        if (job.isComplete() && job.isValid()) return job
//
//        job.step()
//
//        val variable = job.selectUnassignedVariable() ?: return null
//        variable.value.values.forEach {
//            val attempt = Selected(it)
//            job.assignVariable(variable.key, attempt)
//            if (job.isPartiallyValid()) {
//                val result = run(job)
//                if (result != null) {
//                    return result
//                }
//            }
//            job.assignVariable(variable.key, variable.value)
//        }
//        return null
//    }
//}