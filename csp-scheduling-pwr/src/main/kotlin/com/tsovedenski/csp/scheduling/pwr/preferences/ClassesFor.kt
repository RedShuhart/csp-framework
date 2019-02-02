package com.tsovedenski.csp.scheduling.pwr.preferences

import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.GeneralConstraint
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import java.time.DayOfWeek

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
class ClassesFor(val dayOfWeek: DayOfWeek) {

    fun atMost(n: Int) = generateConstraint { it <= n }

    fun atLeast(n: Int) = generateConstraint { it >= n }

    fun exactly(n: Int) = generateConstraint { it == n }

    private fun generateConstraint(predicate: (Int) -> Boolean): Preference {
        return object : Preference() {
            override fun toConstraint(variables: List<Subject>): Constraint<Subject, Group> {
                return GeneralConstraint(variables) {
                    val classes = assignment
                        .values
                        .flatMap { it.toList().filter { it.day == dayOfWeek } }
                        .size
                    predicate(classes)
                }
            }
        }
    }
}