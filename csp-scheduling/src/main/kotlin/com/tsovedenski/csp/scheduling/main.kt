package com.tsovedenski.csp.scheduling

import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.ordering.comparators.BiggestDomainVariable
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.heuristics.pruning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking

fun main(args: Array<String>) {

    val sample = mapOf(
            "Algebra" to generateListOfClasses(),
            "Calculus" to generateListOfClasses(),
            "Physics" to generateListOfClasses(),
            "German" to generateListOfClasses(),
            "Geography" to generateListOfClasses(),
            "History" to generateListOfClasses(),
            "Literature" to generateListOfClasses()
    )

    val problem = Scheduling(sample)

    //writeClassesToFile(problem.classesSchedules, "schedules.txt")


    val s = problem.solve(Backtracking(pruneSchema = PartialLookAhead(), variableOrdering = SmallestDomainVariable()))

    if (s is Solved) {
        printSchedule(s)
        println(s.statistics.pretty(true))
    } else {
        println("No solution")
    }
}
