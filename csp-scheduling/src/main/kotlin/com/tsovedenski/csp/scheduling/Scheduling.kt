package com.tsovedenski.csp.scheduling

import com.tsovedenski.csp.*
import com.tsovedenski.csp.scheduling.TimeRange.Companion.fromString
import java.io.File
import kotlin.random.Random

class Scheduling : Solvable<String, TimeRange> {

//    val classesSchedules = mapOf(
//            "Algebra" to generateListOfClasses(),
//            "Calculus" to generateListOfClasses(),
//            "Physics" to generateListOfClasses(),
//            "German" to generateListOfClasses(),
//            "Geography" to generateListOfClasses(),
//            "History" to generateListOfClasses(),
//            "Literature" to generateListOfClasses()
//    )

    val classesSchedules = readFileToSchedules("schedules.txt")

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

fun List<TimeRange>.toText(): String =
    joinToString(";") { it.print() }

fun printSchedule(solution: Solved<String, TimeRange>) {
    val classes = solution.assignment.toList()
    classes.forEach { println("${it.first} ${it.second.print()}") }
}

fun writeClassesToFile(classesSchedules: Map<String, List<TimeRange>>, filePath: String) {
    File(filePath).printWriter().use { out ->
        classesSchedules
                .toList()
                .map { "${it.first}_${it.second.toText()}" }
                .forEach {out.println(it)}
    }
}
//Algebra_Tuesday 8:30-Tuesday 9:30;Tuesday 8:30-Tuesday 9:45;
fun textToSchedule(text: String): List<TimeRange>
        = text.split(";").map { TimeRange.fromString(it) }

fun toClassSchedulePair(text: String): Pair<String, List<TimeRange>> {
    val split = text.split("_")
    return Pair(split[0], textToSchedule(split[1]))
}

fun readFileToSchedules(filePath: String)
        = File(filePath)
        .bufferedReader()
        .readLines()
        .map { toClassSchedulePair(it) }
        .associateBy({it.first}, {it.second})


