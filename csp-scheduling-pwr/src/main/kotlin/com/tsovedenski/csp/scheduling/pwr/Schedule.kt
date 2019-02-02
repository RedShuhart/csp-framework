package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.*
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import com.tsovedenski.csp.scheduling.pwr.preferences.Preference

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