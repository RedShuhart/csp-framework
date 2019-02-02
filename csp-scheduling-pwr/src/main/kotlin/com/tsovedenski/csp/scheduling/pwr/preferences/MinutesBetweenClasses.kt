package com.tsovedenski.csp.scheduling.pwr.preferences

import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.GeneralConstraint
import com.tsovedenski.csp.print
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.SingleSubject

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
object MinutesBetweenClasses {

    fun atMost(n: Int) = generateConstraint { it <= n }

    fun atLeast(n: Int) = generateConstraint { it >= n }

    fun exactly(n: Int) = generateConstraint { it == n }

    private fun generateConstraint(predicate: (Int) -> Boolean): Preference {
        return object : Preference() {
            override fun toConstraint(variables: List<SingleSubject>): Constraint<SingleSubject, Group> {
                return GeneralConstraint(variables) c@{
                    assignment.print()
                    println()
                    assignment.perDay { it.key }.also { it.forEach(::println) }
                    true
//                    val byDay = assignment.perDay { it.value.zipWithNext { a, b -> a.difference(b)} }
//                        .mapValues { it.value.flatten() }.also { it.forEach(::println) }
//
//                    for (row in byDay.values) {
//                        if (!row.all(predicate)) {
//                            return@c false
//                        }
//                    }
//                    true
                }
            }
        }
    }
}