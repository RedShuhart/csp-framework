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
    val variables = "MSENDMOREMONEY".toList().distinct()
    val domain = (0..9).toList()

    val `all values are distinct`: Constraint = { map ->
        val selecteds = map.filter { it.value is Selected }.values as Collection<Selected>
        val vals = selecteds.map(Selected::value)
        vals.distinct().size == vals.size
    }

    val `m is non-zero`: Constraint = fn@{ map ->
        val M = map.valueOf('M') ?: return@fn false
        M > 0
    }

//    example of bad constraint
//    val `right-most holds`: Constraint = fn@{ map ->
//        val D = map.valueOf('D') ?: return@fn false
//        val E = map.valueOf('E') ?: return@fn false
//        val Y = map.valueOf('Y') ?: return@fn false
//        D + E == Y
//    }

    val `whole sum holds`: Constraint = fn@{ map ->
        val S = map.valueOf('S') ?: return@fn false
        val E = map.valueOf('E') ?: return@fn false
        val N = map.valueOf('N') ?: return@fn false
        val D = map.valueOf('D') ?: return@fn false
        val M = map.valueOf('M') ?: return@fn false
        val O = map.valueOf('O') ?: return@fn false
        val R = map.valueOf('R') ?: return@fn false
        val Y = map.valueOf('Y') ?: return@fn false

        val SEND  =            1000*S + 100*E + 10*N + 1*D
        val MORE  =            1000*M + 100*O + 10*R + 1*E
        val MONEY = 10_000*M + 1000*O + 100*N + 10*E + 1*Y

        SEND + MORE == MONEY
    }

    val task = Task(variables, domain, listOf(
            `m is non-zero`,
            `all values are distinct`,
            `whole sum holds`))

    val solution = solve(task)
    println(when (solution?.isSolved()) {
        true -> "SUCCESS"
        else -> "FAIL"
    })

    solution?.print()
}
typealias Constraint = (Solution) -> Boolean

typealias Solution = MutableMap<Char, Variable>
fun Solution.isSolved(): Boolean = all { it.value is Selected }
fun Solution.print() = forEach { c, variable -> println("$c -> $variable") }.also { println("-----") }
fun emptySolution(): Solution = mutableMapOf()
fun Solution.valueOf(key: Char): Int? = (get(key) as? Selected)?.value

data class Task (val variables: List<Char>, val domain: List<Int>, val constraints: List<Constraint>)
fun Task.toSolution(): Solution = variables.associate { it to Choice(domain) }.toMutableMap()

@Suppress("UNCHECKED_CAST")
data class Job (val solution: Solution, val task: Task, var counter: Long = 0) {
    fun isValid(): Boolean
            = task.constraints.all { it(solution) }

    fun isComplete(): Boolean
            = solution.size == task.variables.size

    fun assignVariable(char: Char, variable: Variable)
            = apply { solution.apply { set(char, variable) } }

    fun selectUnassignedVariable(): Map.Entry<Char, Choice>?
            = solution.filterValues { it is Choice }.entries.firstOrNull() as Map.Entry<Char, Choice>?
}

sealed class Variable
data class Selected(val value: Int) : Variable()
data class Choice(val values: List<Int>) : Variable()

fun solve(task: Task): Solution? {
    val job = Job(task.toSolution(), task)
    val solved = backtrack(job)
    solved?.counter?.let { println("Counter = $it") }
    return solved?.solution
}

fun backtrack(job: Job): Job? {
    if (job.isValid()) return job
    job.counter += 1
    var currentJob = job
    val variable = job.selectUnassignedVariable() ?: return null
    variable.value.values.forEach {
        val attempt = Selected(it)
        currentJob = currentJob.assignVariable(variable.key, attempt)
        val result = backtrack(currentJob)
        if (result != null) {
            return result
        }
        currentJob = currentJob.assignVariable(variable.key, variable.value)
    }

    return null
}