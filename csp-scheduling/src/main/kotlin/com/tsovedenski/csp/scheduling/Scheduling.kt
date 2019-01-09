package com.tsovedenski.csp.scheduling

import com.tsovedenski.csp.*
import kotlin.random.Random

class Scheduling : Solvable<String, TimeRange> {

    private val classesSchedules = mapOf(
            "Algebra" to generateListOfClasses(),
            "Calculus" to generateListOfClasses(),
            "Physics" to generateListOfClasses(),
            "German" to generateListOfClasses(),
            "Geography" to generateListOfClasses(),
            "History" to generateListOfClasses(),
            "Literature" to generateListOfClasses()
    )

    private val classes = classesSchedules.keys.toList()

    private val constraints: List<Constraint<String, TimeRange>> = listOf(AllConstraint(classes, ::areNotOverlapping))

    override fun toProblem() = Problem(classes, constraints, classesSchedules::getValue)
}

fun generateClass(name: String, day: DayOfWeek, start: String, end: String) = name to TimeRange(Boundary(day, start), Boundary(day, end))

fun generateTimeRange(day: DayOfWeek, start: String, end: String) = TimeRange(Boundary(day, start), Boundary(day, end))

fun createSampleOfRanges(day: DayOfWeek, hour: Int) = listOf<TimeRange>(
        generateTimeRange(day, "$hour:15", "${hour + 1}:15"),
        generateTimeRange(day, "$hour:15", "${hour + 1}:30"),
        generateTimeRange(day, "$hour:30", "${hour + 1}:30"),
        generateTimeRange(day, "$hour:30", "${hour + 1}:45"),
        generateTimeRange(day, "$hour:00", "${hour + 1}:00"),
        generateTimeRange(day, "$hour:00", "${hour + 1}:30"),
        generateTimeRange(day, "$hour:45", "${hour + 1}:45")
)

fun generateListOfClasses() = (0..6).map { day -> (6..22).map { hour -> createSampleOfRanges(DayOfWeek.fromInt(day), hour) }.flatten() }
        .flatten().reduceList()

fun List<TimeRange>.reduceList(): List<TimeRange> {
    val first = Random.nextInt(size / 2)
    val second = Random.nextInt(size / 2) + first
    return subList(first, second)
}

fun printSchedule(solution: Solved<String, TimeRange>) {
    val classes = solution.assignment.toList()
    classes.forEach { println("${it.first} ${it.second.print()}") }
}
