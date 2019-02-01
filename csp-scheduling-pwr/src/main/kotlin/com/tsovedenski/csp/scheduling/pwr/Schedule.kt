package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.*
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Type
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import java.time.DayOfWeek
import kotlin.math.abs

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
class Schedule(
        private val courses: List<Course>,
        private val maxPerDay: Int,
        private val maxPerFriday: Int
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

    private val constraints = listOf<Constraint<SingleSubject, Group>>(
            // classes should not overlap
            AllConstraint { a, b ->
                if (a.week == b.week) !a.overlaps(b)
                else true
            },

            // amount of classes each day each week (odd/even)
            // should be less than maxPerDay
            // TODO: refactor...
            GeneralConstraint(variables) c@{
                val variablesByDay: Map<Pair<DayOfWeek, Week>, List<SingleSubject>> = assignment
                        .mapValues { it.value.toList() }
                        .map { row -> row.value.map { group -> (group.day to group.week) to row.key }.toMap() }
                        .fold(mutableMapOf<Pair<DayOfWeek, Week>, MutableList<SingleSubject>>()) { acc, row ->
                            row.forEach { (day, week), group ->
                                val oddDay = day to Week.Odd
                                val evenDay = day to Week.Even
                                if (!acc.containsKey(oddDay)) {
                                    acc[oddDay] = mutableListOf()
                                }
                                if (!acc.containsKey(evenDay)) {
                                    acc[evenDay] = mutableListOf()
                                }

                                when (week) {
                                    Week.Odd -> acc.getValue(oddDay).add(group)
                                    Week.Even -> acc.getValue(evenDay).add(group)
                                    Week.Both -> {
                                        acc.getValue(oddDay).add(group)
                                        acc.getValue(evenDay).add(group)
                                    }
                                }
                            }
                            acc
                        }
                        .mapValues { it.value.toList() }

                for (v in variablesByDay.values) {
                    if (v.size > maxPerDay) {
                        return@c false
                    }
                }
                true
            },

            // prefer to have less classes on friday
            // TODO: generalize as class parameter
            GeneralConstraint(variables) {
                val fridays = assignment
                        .values
                        .flatMap { it.toList().filter { it.day == DayOfWeek.FRIDAY } }
                        .size
                fridays <= maxPerFriday
            },

            GeneralConstraint(variables) c@{
                println(1)
                val byDay: Map<DayOfWeek, List<Group>> = assignment
                        .mapValues { it.value.toList() }
                        .map { row -> row.value.map { group -> group.day to row.value }.toMap() }
                        .fold(mutableMapOf<DayOfWeek, MutableList<Group>>().withDefault { mutableListOf() }) { acc, row ->
                            row.forEach { day, groups ->
                                acc.getValue(day).addAll(groups)
                            }
                            acc
                        }
                byDay.forEach(::println)
                for (r in byDay) {
                    val zipped = r.value.zipWithNext { a, b -> a.difference(b) }
                    if (!zipped.all { it < 5 }) {
                        return@c false
                    }
                }
                true
            }
    )

    override fun toProblem(): Problem<SingleSubject, Group>
            = Problem(variablesWithDomains, constraints)
}

data class SingleSubject(val name: String, val type: Type)