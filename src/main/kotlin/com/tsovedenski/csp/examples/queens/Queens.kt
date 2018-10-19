package com.tsovedenski.csp.examples.queens

import com.tsovedenski.csp.AllDiffConstraint
import com.tsovedenski.csp.AllIndexedConstraint
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.Task
import kotlin.math.abs


/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
class Queens(val size: Int) : Task<Int, Int>() {

    override val variables = (0 until size).toList()

    override val domain = (0 until size).toList()

    override val constraints: List<Constraint<Int, Int>> = listOf(
        AllDiffConstraint(variables),

        AllIndexedConstraint(variables) { (a, i), (b, j) ->
            abs(a - b) != abs(i - j)
        }
    )

}