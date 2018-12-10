package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.*
import kotlin.math.abs


/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
class Queens(size: Int) : Solvable<Int, Int> {

    private val variables = (0 until size).toList()

    private val domain = (0 until size).toList()

    private  val constraints: List<Constraint<Int, Int>> = listOf(
        AllDiffConstraint(variables.toList()),

        AllIndexedConstraint(variables.toList()) { (a, i), (b, j) ->
            abs(a - b) != abs(i - j)
        }
    )

    override fun toProblem(): Problem<Int, Int> = Problem(variables, domain, constraints)
}
