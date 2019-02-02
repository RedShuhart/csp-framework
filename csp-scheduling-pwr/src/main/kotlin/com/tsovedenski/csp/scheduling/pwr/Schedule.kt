package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.*
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import com.tsovedenski.csp.scheduling.pwr.preferences.Preference
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.min
import kotlin.math.max

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
class Schedule(
    val courses: List<Course>,
    vararg preferences: Preference
) : Solvable<Subject, Group> {

    private val variablesWithDomains = courses
        .map { it.name to it.classes }
        .toMap()
        .map { course -> course.value.map { Subject(course.key, it.key) to it.value }.toMap() }
        .fold(mutableMapOf<Subject, List<Group>>()) { acc, map ->
            map.forEach { k, v -> acc[k] = v.toList() }
            acc
        }
        .toMap()

    private val variables = variablesWithDomains.keys.toList()

    private val shouldNotOverlap = AllConstraint<Subject, Group>(variables) { a, b ->
        !a.overlaps(b)
    }

    private val constraints = listOf(shouldNotOverlap) + preferences.map { it.toConstraint(variables) }

    override fun toProblem(): Problem<Subject, Group> = Problem(variablesWithDomains, constraints)
}

fun CompleteAssignment<Subject, Group>.asString(): String = buildString {
    this@asString
        .toList()
        .groupBy { it.second.day }
        .toSortedMap()
        .forEach { day, groups ->
            appendln(day)
            groups.sortedBy { it.second.startAt }.forEach { (subject, group) ->
                appendln("-- $group (${subject.type} - ${subject.name})")
            }
            appendln()
        }
}

fun CompleteAssignment<Subject, Group>.asTable(): String = buildString {
    val columnWidth = 15
    val columns = 5

    val map = this@asTable
        .toList()
        .groupBy { it.second.day }
        .toSortedMap()
        .mapValues { drawTable(it.key, it.value).split("\n") }
        .toMutableMap()

    val days = listOf(
        null,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    )

    val height = map.toList().first().second.size
    val hours = drawTable(null, listOf()).split("\n")

    repeat(height) { i ->
        days.forEach { day ->
            if (day == null) {
                val line = hours[i]
                append(line)
            } else {
                val line = map[day]?.get(i)
                if (line != null) {
                    append(line)
                }
            }
        }
        appendln()
    }
}

fun drawTable(day: DayOfWeek?, groups: List<Pair<Subject, Group>>) = buildString {
    val width = 15
    val startTimes = listOf(
        LocalTime.of(7, 30),
        LocalTime.of(9, 15),
        LocalTime.of(10, 15),
        LocalTime.of(11, 15),
        LocalTime.of(13, 15),
        LocalTime.of(15, 15),
        LocalTime.of(17, 5),
        LocalTime.of(18, 55)
    )

    if (day != null) {
        appendln("-".repeat(width))
        appendln("|  $day ${" ".repeat(width - day.toString().length - 5)}|")

        var i = 0
        startTimes.forEach { time ->
            appendln("-".repeat(width))
            if (i < groups.size && groups[i].second.startAt == time) {
                val (subject, group) = groups[i]
                val week = group.week
                val weekString = when (week) {
                    Week.Both -> ""
                    else -> " $week"
                }
                var line1 = "${subject.type.code} ${subject.abbr}$weekString"
                var line2 = "${group.code}"

                line1 = line1.substring(0, min(line1.length, width - 2))
                line2 = line2.substring(0, min(line2.length, width - 2))

                appendln("| " + line1 + " ".repeat(max(0, width - 4 - line1.length)) + " |")
                appendln("| " + line2 + " ".repeat(max(0, width - 4 - line2.length)) + " |")
                i++
            } else {
                repeat(2) {
                    appendln("|" + " ".repeat(width - 2) + "|")
                }
            }
        }
        appendln("-".repeat(width))
    } else {
        val smallerWidth = width - 5
        appendln("-".repeat(smallerWidth - 3))
        appendln("${" ".repeat(smallerWidth - 4)}|")

        startTimes.forEach { time ->
            appendln("-".repeat(smallerWidth - 3))
            val line = time.format(DateTimeFormatter.ISO_LOCAL_TIME)

            appendln("$time" + " ".repeat(smallerWidth - line.length - 1) + "|")
            appendln(" ".repeat(smallerWidth - 4) + "|")
        }
        appendln("-".repeat(smallerWidth - 3))
    }
}