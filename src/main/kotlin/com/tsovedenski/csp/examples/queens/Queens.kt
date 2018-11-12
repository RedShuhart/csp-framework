package com.tsovedenski.csp.examples.queens

import com.tsovedenski.csp.*
import kotlin.math.abs


/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
class Queens(val size: Int) : Task<Int, Int>() {

    override val variables: Map<Int, List<Int>> = (0 until size).toList()
            .toDomain((0 until size).toList())

    override val constraints: List<Constraint<Int, Int>> = listOf(
        AllDiffConstraint(variables.keys.toList()),

        AllIndexedConstraint(variables.keys.toList()) { (a, i), (b, j) ->
            abs(a - b) != abs(i - j)
        }
    )

}
