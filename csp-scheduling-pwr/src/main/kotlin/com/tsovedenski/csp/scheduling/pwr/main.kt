package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Subject
import com.tsovedenski.csp.scheduling.pwr.domain.Type.*
import com.tsovedenski.csp.scheduling.pwr.domain.Week.*
import java.time.DayOfWeek.*

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
fun main(args: Array<String>) {
    val courses = listOf(
        Course(
            name = "Foundation of Knowledge Engineering",
            classes = mapOf(
                Lecture to setOf(Group("Z04-62a", TUESDAY, "9:15-11:00")),
                Class to setOf(
                    Group("Z04-61a", THURSDAY, "11:15-13:00"),
                    Group("Z04-61b", THURSDAY, "13:15-15:00")
                )
            )
        ),

        Course(
            name = "Modelling and Analysis of Web-based Systems",
            classes = mapOf(
                Lecture to setOf(Group("Z04-53a", WEDNESDAY, "13:15-15:00")),
                Lab to setOf(
                    Group("Z04-52a", FRIDAY, "9:15-11:00"),
                    Group("Z04-52b", FRIDAY, "11:15-13:00"),
                    Group("Z04-52c", FRIDAY, "13:15-15:00"),
                    Group("Z04-52d", WEDNESDAY, "9:15-11:00")
                )
            )
        ),

        Course(
            name = "Parallel and Distributed Computing",
            classes = mapOf(
                Lecture to setOf(Group("Z04-56a", THURSDAY, "15:15-16:55")),
                Class to setOf(
                    Group("Z04-54a", THURSDAY, "17:05-18:45", Odd),
                    Group("Z04-54b", THURSDAY, "17:05-18:45", Even)
                ),
                Lab to setOf(
                    Group("Z04-55a", WEDNESDAY, "17:05-18:45", Odd),
                    Group("Z04-55b", WEDNESDAY, "17:05-18:45", Even),
                    Group("Z04-55c", WEDNESDAY, "18:55-20:35", Even),
                    Group("Z04-55d", WEDNESDAY, "15:15-16:55", Even)
                )
            )
        ),

        Course(
            name = "Mobile and Multimedia Systems",
            classes = mapOf(
                Lecture to setOf(Group("Z04-58a", THURSDAY, "9:15-11:00", Odd)),
                Lab to setOf(
                    Group("Z04-57a", TUESDAY, "13:15-16:00"),
                    Group("Z04-57b", FRIDAY, "7:30-10:00"),
                    Group("Z04-57c", WEDNESDAY, "15:15-17:50"),
                    Group("Z04-57d", WEDNESDAY, "18:00-20:35")
                )
            )
        ),

        Course(
            name = "Software System Development",
            classes = mapOf(
                Lecture to setOf(Group("Z04-60a", TUESDAY, "17:05-18:45")),
                Project to setOf(
                    Group("Z04-59a", FRIDAY, "11:15-13:00"),
                    Group("Z04-59b", TUESDAY, "7:30-9:00"),
                    Group("Z04-59c", FRIDAY, "7:30-9:00"),
                    Group("Z04-59d", FRIDAY, "9:15-11:00")
                )
            )
        )
    )

    val perDay = courses
            .map(Course::classes)
            .flatMap { it.values }
            .flatten()
            .groupBy { it.day }
            .mapValues { it.value.size }
            .let(::println)
}