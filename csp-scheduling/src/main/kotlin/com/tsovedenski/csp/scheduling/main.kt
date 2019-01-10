package com.tsovedenski.csp.scheduling

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.ordering.comparators.BiggestDomainVariable
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

fun main(args: Array<String>) {
    val problem = Scheduling()

    writeClassesToFile(problem.classesSchedules, "schedules.txt")



    val s = problem.solve(Backtracking(variableOrdering = BiggestDomainVariable()))

    if (s is Solved) {
        printSchedule(s)
        println(s.statistics.pretty(true))
    } else {
        println("No solution")
    }
}
