package com.tsovedenski.csp.scheduling.pwr.domain

import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
data class Subject (
        val name: String,
        val type: Type,
        val groups: Set<Group>
) {
    companion object {
        private const val SEPARATOR = "|"
        private const val GROUP_SEPARATOR = "+"
        fun fromString(value: String): Subject {
            val parts = value.split(SEPARATOR)
            return Subject(
                    parts[0],
                    Type.valueOf(parts[1]),
                    parts[2].split(GROUP_SEPARATOR).map(Group.Companion::fromString).toSet()
            )
        }
    }

    fun serialize() = listOf(
        name, type.toString(),
        groups.joinToString(GROUP_SEPARATOR, transform = Group::serialize)
    ).joinToString(SEPARATOR)
}