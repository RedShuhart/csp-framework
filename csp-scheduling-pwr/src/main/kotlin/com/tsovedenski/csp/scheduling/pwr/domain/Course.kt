package com.tsovedenski.csp.scheduling.pwr.domain

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
data class Course(
        val name: String,
        val classes: Map<Type, Set<Group>>
)