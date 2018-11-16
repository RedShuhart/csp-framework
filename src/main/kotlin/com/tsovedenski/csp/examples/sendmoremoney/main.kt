package com.tsovedenski.csp.examples.sendmoremoney

import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.strategies.Backtracking
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Created by Tsvetan Ovedenski on 13/10/18.
 *
 *
 *       S E N D
 *   +   M O R E
 *   = M O N E Y
 */
fun main(args: Array<String>) {
    val problem = WordSum("SEND", "MORE", "MONEY")
    val solution = problem.solve(strategy = Backtracking(variableOrdering = SmallestDomainVariable()))
    solution.print()
}
