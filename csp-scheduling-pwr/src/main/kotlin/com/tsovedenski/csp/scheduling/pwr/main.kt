package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.preferences.ClassesFor
import com.tsovedenski.csp.scheduling.pwr.preferences.DailyClasses
import com.tsovedenski.csp.scheduling.pwr.preferences.MinutesBetweenClasses
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking
import java.time.DayOfWeek.*

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
fun main() {
    val problem = Schedule(
        courses
        , DailyClasses.atMost(3)
        , ClassesFor(FRIDAY).atMost(2)
        , MinutesBetweenClasses.atLeast(10)
    )
    val solution = problem.solve(
        Backtracking(
            pruneSchema = FullLookAhead()
        )
    )

    if (solution is Solved) {
        solution.assignment.asTable().also(::println)
        solution.statistics.print()
    } else {
        println("No solution :(")
    }
}