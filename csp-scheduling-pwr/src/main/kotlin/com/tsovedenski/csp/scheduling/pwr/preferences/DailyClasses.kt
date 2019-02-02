package com.tsovedenski.csp.scheduling.pwr.preferences

import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.GeneralConstraint
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.SingleSubject

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
object DailyClasses {
    fun atMost(n: Int) = generateConstraint { it <= n }

    fun atLeast(n: Int) = generateConstraint { it >= n }

    fun exactly(n: Int) = generateConstraint { it == n }

    private fun generateConstraint(predicate: (Int) -> Boolean): Preference {
        return object : Preference() {
            override fun toConstraint(variables: List<SingleSubject>): Constraint<SingleSubject, Group> {
                return GeneralConstraint(variables) c@{
                    val subjectsPerDay = assignment.perDay { it.key }

                    for (v in subjectsPerDay.values) {
                        if (!predicate(v.size)) {
                            return@c false
                        }
                    }
                    true
                }
            }
        }
    }
}