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

    val `all values are distinct`: Constraint<Char, Int> = { map ->
        val selecteds = map.filter { it.value is Selected }.values as Collection<Selected<Int>>
        val vals = selecteds.map(Selected<Int>::value)
        vals.distinct().size == vals.size
    }

    val `m is non-zero`: Constraint<Char, Int> = fn@{ map ->
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

    val `whole sum holds`: Constraint<Char, Int> = fn@{ map ->
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
typealias Constraint <V, D> = (Solution<V, D>) -> Boolean

typealias Solution <V, D> = MutableMap<V, Variable<D>>
fun <V, D> Solution<V, D>.isSolved(): Boolean = all { it.value is Selected }
fun <V, D> Solution<V, D>.print() = forEach { c, variable -> println("$c -> $variable") }.also { println("-----") }
fun <V, D> emptySolution(): Solution<V, D> = mutableMapOf()
fun <V, D> Solution<V, D>.valueOf(key: V): D? = (get(key) as? Selected<D>)?.value

data class Task <V, D> (val variables: List<V>, val domain: List<D>, val constraints: List<Constraint<V, D>>)
fun <V, D> Task<V, D>.toSolution(): Solution<V, D> = variables.associate { it to Choice(domain) }.toMutableMap()

@Suppress("UNCHECKED_CAST")
data class Job <V, D> (val solution: Solution<V, D>, val task: Task<V, D>, var counter: Long = 0) {
    fun isValid(): Boolean
            = task.constraints.all { it(solution) }

    fun isComplete(): Boolean
            = solution.size == task.variables.size

    fun assignVariable(key: V, value: Variable<D>)
            = apply { solution[key] = value }

    fun selectUnassignedVariable(): Map.Entry<V, Choice<D>>?
            = solution.filterValues { it is Choice }.entries.firstOrNull() as Map.Entry<V, Choice<D>>?
}

sealed class Variable <A>
data class Selected <A> (val value: A) : Variable<A>()
data class Choice <A> (val values: List<A>) : Variable<A>()

fun <V, D> solve(task: Task<V, D>): Solution<V, D>? {
    val job = Job(task.toSolution(), task)
    val solved = backtrack(job)
    solved?.counter?.let { println("Counter = $it") }
    return solved?.solution
}

fun <V, D> backtrack(job: Job<V, D>): Job<V, D>? {
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