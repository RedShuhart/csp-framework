package com.tsovedenski.csp.examples.sendmoremoney

import com.tsovedenski.csp.*
import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.strategies.MostFamousVariable
import com.tsovedenski.csp.strategies.SmallestDomainVariable
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
    val task = WordSum("SEND", "MORE", "MONEY")
    val solution = task.solve(strategy = Backtracking(variableOrdering = SmallestDomainVariable()))
    solution.print()
}