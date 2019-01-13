package com.tsovedenski.csp.scheduling

import com.tsovedenski.csp.AllConstraint
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.Problem
import com.tsovedenski.csp.Solvable

class SchedulingV2 : Solvable<String, TimeRange> {

    private val subjects = listOf("Algebra", "Calculus", "Physics", "German", "Geography", "History", "Literature")
    private val classes = listOf("7A", "7B"/*, "8A", "8B", "8C", "9A", "10A", "11A"*/)

    private val classSubjects = classes.flatMap { clazz -> subjects.map { subject -> "${clazz}_$subject" }}

    private val constraints: List<Constraint<String, TimeRange>> = listOf(AllConstraint(classSubjects, ::areNotOverlapping))

    override fun toProblem() = Problem(classSubjects, constraints) { generateListOfClasses() }
}