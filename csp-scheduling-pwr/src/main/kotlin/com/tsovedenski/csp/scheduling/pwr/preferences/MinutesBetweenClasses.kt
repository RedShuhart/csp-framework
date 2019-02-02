package com.tsovedenski.csp.scheduling.pwr.preferences

import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.GeneralConstraint
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import java.time.DayOfWeek

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
object MinutesBetweenClasses {

    fun atMost(n: Int) = generateConstraint { it <= n }

    fun atLeast(n: Int) = generateConstraint { it >= n }

    fun exactly(n: Int) = generateConstraint { it == n }

    private fun generateConstraint(predicate: (Int) -> Boolean): Preference {
        return object : Preference() {
            override fun toConstraint(variables: List<Subject>): Constraint<Subject, Group> {
                return GeneralConstraint(variables) c@{
                    val byDay: Map<Pair<DayOfWeek, Week>, List<Group>> = assignment.perDay { it.value }

                    for (row in byDay.values) {
                        val zipped = row.zipWithNext { a, b -> a.difference(b) }
                        if (!zipped.all(predicate)) {
                            return@c false
                        }
                    }
                    true
                }
            }
        }
    }
}