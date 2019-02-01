package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import com.tsovedenski.csp.scheduling.pwr.domain.Type.*
import com.tsovedenski.csp.scheduling.pwr.domain.Week.*
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking
import java.time.DayOfWeek.*

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
fun main(args: Array<String>) {
    val problem = Schedule(
            courses,
            maxPerDay = 3,
            maxPerFriday = 2
    )
    val solution = problem.solve(Backtracking(
            pruneSchema = FullLookAhead()
    ))

    if (solution is Solved) {
        solution.assignment
                .toList()
                .groupBy { it.second.day }
                .toSortedMap()
                .forEach { day, groups ->
                    println(day)
                    groups.sortedBy { it.second.startAt }.forEach { (subject, group) ->
                        println("-- $group (${subject.type} - ${subject.name})")
                    }
                    println()
                }
    }
}