package com.tsovedenski.csp

/**
 * Created by Tsvetan Ovedenski on 13/10/18.
 *
 *
 *       S E N D
 *   +   M O R E
 *   = M O N E Y
 */
fun main(args: Array<String>) {
    val variables = "SENDMOREMONEY".toList().distinct()
    val domain = (1..9).toList()

    val constrDplusEeqY: (Solution) -> Boolean = { map ->
        val d = map.getValue('D')
        val e = map.getValue('E')
        val y = map.getValue('Y')
        d is Selected && e is Selected && y is Selected && d.value + e.value == y.value
    }
    val constrTheSum: (Solution) -> Boolean = fn@{ map ->
        val S = (map.getValue('S') as? Selected)?.value ?: return@fn false
        val E = (map.getValue('E') as? Selected)?.value ?: return@fn false
        val N = (map.getValue('N') as? Selected)?.value ?: return@fn false
        val D = (map.getValue('D') as? Selected)?.value ?: return@fn false
        val M = (map.getValue('M') as? Selected)?.value ?: return@fn false
        val O = (map.getValue('O') as? Selected)?.value ?: return@fn false
        val R = (map.getValue('R') as? Selected)?.value ?: return@fn false
        val Y = (map.getValue('Y') as? Selected)?.value ?: return@fn false

        val SEND  =            1000*S + 100*E + 10*N + 1*D
        val MORE  =            1000*M + 100*O + 10*R + 1*E
        val MONEY = 10_000*M + 1000*O + 100*N + 10*E + 1*Y

        SEND + MORE == MONEY
    }

    val task = Task(variables, domain, listOf(constrDplusEeqY, constrTheSum))

    val solution = solve(task)
    println(when (solution?.isSolved()) {
        true -> "SUCCESS"
        else -> "FAIL"
    })

    solution?.forEach { c, variable ->
        println("$c -> $variable")
    }
}

typealias Solution = Map<Char, Variable>
fun Solution.isSolved(): Boolean = all { it.value is Selected }

data class Task (val variables: List<Char>, val domain: List<Int>, val constraints: List<(Solution) -> Boolean>) {
    fun toSolution(): Solution = variables.associate { it to Choice(domain) }
}

data class Job (val solution: Solution, val constraints: List<(Solution) -> Boolean>) {
    fun isValid(): Boolean = constraints.all { it(solution) }
}

sealed class Variable
data class Selected(val value: Int) : Variable()
data class Choice(val values: List<Int>) : Variable()

fun solve(task: Task): Solution? {
    val job = Job(task.toSolution(), task.constraints)

    val solution = backtrack(job)
    return solution
}

fun backtrack(job: Job): Solution? {
    if (!job.isValid()) {
        return null
    }
    if (job.solution.isSolved()) {
        return job.solution
    }

    val next = selectValue(job)
    return backtrack(job)
}

fun selectValue(job: Job): Job? = job.copy(
        solution = job.solution.mapValues(::choose)
)

fun choose(entry: Map.Entry<Char, Variable>): Variable = when (entry.value) {
    is Selected -> entry.value
    is Choice   -> Selected((entry.value as Choice).values.first())
}