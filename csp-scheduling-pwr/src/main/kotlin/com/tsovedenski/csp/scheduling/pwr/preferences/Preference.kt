package com.tsovedenski.csp.scheduling.pwr.preferences

import com.tsovedenski.csp.Assignment
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import java.time.DayOfWeek

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
abstract class Preference {
    abstract fun toConstraint(variables: List<Subject>): Constraint<Subject, Group>
}

internal fun <T> Assignment<Subject, Group>.perDay(f: (Map.Entry<Subject, Group>) -> T) = this
    .mapValues { it.value.toList().first() }
    .map { row -> (row.value.day to row.value.week) to f(row) }
    .fold(
        mutableMapOf<Pair<DayOfWeek, Week>, MutableList<T>>()
    ) { acc, t ->
        val (day, week) = t.first
        val oddDay = day to Week.Odd
        val evenDay = day to Week.Even
        if (!acc.containsKey(oddDay)) acc[oddDay] = mutableListOf()
        if (!acc.containsKey(evenDay)) acc[evenDay] = mutableListOf()
        when (week) {
            Week.Odd -> acc.getValue(oddDay).add(t.second)
            Week.Even -> acc.getValue(evenDay).add(t.second)
            Week.Both -> {
                acc.getValue(oddDay).add(t.second)
                acc.getValue(evenDay).add(t.second)
            }
        }
        acc
    }
    .mapValues { it.value.toList() }
