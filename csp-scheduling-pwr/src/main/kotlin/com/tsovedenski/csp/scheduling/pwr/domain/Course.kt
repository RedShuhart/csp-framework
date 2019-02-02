package com.tsovedenski.csp.scheduling.pwr.domain

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
data class Course(
    val name: String,
    val classes: Map<Type, Set<Group>>
) {
    companion object {
        private val SEPARATOR = "|"
        private val GROUP_SEPARATOR = "@"
        private val CLASS_SEPARATOR = "$"
        private val PAIR_SEPARATOR = "%"
        fun fromString(value: String): Course {
            val parts = value.split(SEPARATOR)
            val pairs: List<Pair<Type, Set<Group>>> = parts[1]
                .split(CLASS_SEPARATOR)
                .flatMap { it.split(PAIR_SEPARATOR).zipWithNext() }
                .map { (type, set) -> Pair(
                    Type.valueOf(type),
                    set.split(GROUP_SEPARATOR).map(Group.Companion::fromString).toSet()) }
            return Course(
                parts[0],
                pairs.toMap()
            )
        }
    }

    fun serialize() = listOf(
        name,
        classes.toList().joinToString(CLASS_SEPARATOR) { (type, set) ->
            val groups = set.joinToString(GROUP_SEPARATOR, transform = Group::serialize)
            "$type$PAIR_SEPARATOR$groups"
        }
    ).joinToString(SEPARATOR)
}