package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.AllConstraint
import com.tsovedenski.csp.Problem
import com.tsovedenski.csp.Solvable
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.SingleSubject
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import com.tsovedenski.csp.scheduling.pwr.preferences.Preference

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
class Schedule(
        val courses: List<Course>,
        vararg preferences: Preference
) : Solvable<SingleSubject, Group> {

    private val variablesWithDomains = courses
            .map { it.name to it.classes }
            .toMap()
            .map { course -> course.value.map { SingleSubject(course.key, it.key) to it.value }.toMap() }
            .fold(mutableMapOf<SingleSubject, List<Group>>()) { acc, map ->
                map.forEach { k, v -> acc[k] = v.toList() }
                acc
            }
            .toMap()

    private val variables = variablesWithDomains.keys.toList()

    private val shouldNotOverlap = AllConstraint<SingleSubject, Group> { a, b ->
        !a.overlaps(b)
    }

    private val constraints = listOf(shouldNotOverlap) + preferences.map { it.toConstraint(variables) }

    override fun toProblem(): Problem<SingleSubject, Group>
            = Problem(variablesWithDomains, constraints)
}