package com.tsovedenski.csp.wordsum

import com.tsovedenski.csp.heuristics.prouning.schemas.FullLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

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
    val solution = problem.solve(
        strategy = Backtracking(
//            variableOrdering = MostFrequentVariable(),
            pruneSchema = FullLookAhead()
        )
    )
    solution.print()
}
