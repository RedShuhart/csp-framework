package com.tsovedenski.csp.scheduling.pwr

import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Type
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import java.time.DayOfWeek

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
val courses = listOf(
        Course(
                name = "Foundation of Knowledge Engineering",
                classes = mapOf(
                        Type.Lecture to setOf(Group("Z04-62a", DayOfWeek.TUESDAY, "9:15-11:00")),
                        Type.Class to setOf(
                                Group("Z04-61a", DayOfWeek.THURSDAY, "11:15-13:00"),
                                Group("Z04-61b", DayOfWeek.THURSDAY, "13:15-15:00")
                        )
                )
        ),

        Course(
                name = "Modelling and Analysis of Web-based Systems",
                classes = mapOf(
                        Type.Lecture to setOf(Group("Z04-53a", DayOfWeek.WEDNESDAY, "13:15-15:00")),
                        Type.Lab to setOf(
                                Group("Z04-52a", DayOfWeek.FRIDAY, "9:15-11:00"),
                                Group("Z04-52b", DayOfWeek.FRIDAY, "11:15-13:00"),
                                Group("Z04-52c", DayOfWeek.FRIDAY, "13:15-15:00"),
                                Group("Z04-52d", DayOfWeek.WEDNESDAY, "9:15-11:00")
                        )
                )
        ),

        Course(
                name = "Parallel and Distributed Computing",
                classes = mapOf(
                        Type.Lecture to setOf(Group("Z04-56a", DayOfWeek.THURSDAY, "15:15-16:55")),
                        Type.Class to setOf(
                                Group("Z04-54a", DayOfWeek.THURSDAY, "17:05-18:45", Week.Odd),
                                Group("Z04-54b", DayOfWeek.THURSDAY, "17:05-18:45", Week.Even)
                        ),
                        Type.Lab to setOf(
                                Group("Z04-55a", DayOfWeek.WEDNESDAY, "17:05-18:45", Week.Odd),
                                Group("Z04-55b", DayOfWeek.WEDNESDAY, "17:05-18:45", Week.Even),
                                Group("Z04-55c", DayOfWeek.WEDNESDAY, "18:55-20:35", Week.Even),
                                Group("Z04-55d", DayOfWeek.WEDNESDAY, "15:15-16:55", Week.Even)
                        )
                )
        ),

        Course(
                name = "Mobile and Multimedia Systems",
                classes = mapOf(
                        Type.Lecture to setOf(Group("Z04-58a", DayOfWeek.THURSDAY, "9:15-11:00", Week.Odd)),
                        Type.Lab to setOf(
//                                Group("Z04-57a", DayOfWeek.TUESDAY, "13:15-16:00"),
                                Group("Z04-57b", DayOfWeek.FRIDAY, "7:30-10:00"),
                                Group("Z04-57c", DayOfWeek.WEDNESDAY, "15:15-17:50"),
                                Group("Z04-57d", DayOfWeek.WEDNESDAY, "18:00-20:35")
                        )
                )
        ),

        Course(
                name = "Software System Development",
                classes = mapOf(
                        Type.Lecture to setOf(Group("Z04-60a", DayOfWeek.TUESDAY, "17:05-18:45")),
                        Type.Project to setOf(
                                Group("Z04-59a", DayOfWeek.FRIDAY, "11:15-13:00"),
                                Group("Z04-59b", DayOfWeek.TUESDAY, "7:30-9:00"),
                                Group("Z04-59c", DayOfWeek.FRIDAY, "7:30-9:00"),
                                Group("Z04-59d", DayOfWeek.FRIDAY, "9:15-11:00")
                        )
                )
        )
)