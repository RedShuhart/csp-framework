package com.tsovedenski.csp.scheduling.pwr.preferences

import com.tsovedenski.csp.Assignment
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.SingleSubject
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import java.time.DayOfWeek

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
abstract class Preference {
    abstract fun toConstraint(variables: List<SingleSubject>): Constraint<SingleSubject, Group>
}

internal fun <T> Assignment<SingleSubject, Group>.perDay(f: (Map.Entry<SingleSubject, List<Group>>) -> T) = this
    .mapValues { it.value.toList() }
    .map { row -> row.value.map { group -> (group.day to group.week) to f(row) }.toMap() }
    .fold(
        mutableMapOf<Pair<DayOfWeek, Week>, MutableList<T>>()
    ) { acc, row ->
        row.forEach { (day, week), group ->
            val oddDay = day to Week.Odd
            val evenDay = day to Week.Even
            if (!acc.containsKey(oddDay)) acc[oddDay] = mutableListOf()
            if (!acc.containsKey(evenDay)) acc[evenDay] = mutableListOf()
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
