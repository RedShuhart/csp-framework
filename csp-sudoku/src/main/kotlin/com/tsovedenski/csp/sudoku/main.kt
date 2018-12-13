package com.tsovedenski.csp.sudoku

import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Solution
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.benchmark.Benchmark
import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.heuristics.prouning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.prouning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.prouning.schemas.PartialLookAhead
import com.tsovedenski.csp.solve

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
fun main(args: Array<String>) {
    val problem = Sudoku(sudoku1)

//    runSolution(problem)
    runBenchmarks(problem, 1)
}

fun runSolution(problem: Sudoku) {
    val solution = problem.solve(
        strategy = Backtracking(
//            variableOrdering = SmallestDomainVariable(),
            pruneSchema = FullLookAhead()
        )
    )
    solution.print()
    printSudoku(problem.grid, solution)
}

fun runBenchmarks(problem: Sudoku, runs: Int) {
    val benchmark = Benchmark(problem, runs, 1, mapOf(
            "no prune" to Backtracking(),
            "FC"       to Backtracking(pruneSchema = ForwardChecking()),
            "PLA"      to Backtracking(pruneSchema = PartialLookAhead()),
            "FLA"      to Backtracking(pruneSchema = FullLookAhead())
    ))

    benchmark.execute().prettyPrint()
}

// easy
val sudoku1 = listOf(
        "18xxx57x6",
        "x2x7xx348",
        "x63428xxx",
        "xx52479xx",
        "x92x8x41x",
        "3x791xxx5",
        "9xxxx2174",
        "47xxx9x52",
        "2x8174xxx"
)

// hard
val sudoku2 = listOf(
        "xxx2xxx63",
        "3xxxx54x1",
        "xx1xx398x",
        "xxxxxxx9x",
        "xxx538xxx",
        "x3xxxxxxx",
        "x263xx5xx",
        "5x37xxxx8",
        "47xxx1xxx"
)

val sudoku3 = listOf(
        "x4xxxx179",
        "xx2xx8x54",
        "xx6xx5xx8",
        "x8xx7x91x",
        "x5xx9xx3x",
        "x19x6xx4x",
        "3xx4xx7xx",
        "57x1xx2xx",
        "928xxxx6x"
)

fun printSudoku(grid: List<String>, solution: Solution<String, Int>) {
    grid.forEach {
        println(it)
    }
    println()
    (solution as? Solved)?.let { solved ->
        val solvedGrid = grid.toMutableList().map { it.toMutableList() }
        solved.assignment.forEach { p, n ->
            val (r, c) = p.toList().map { it.toInt() - 48 }
            solvedGrid[r][c] = (n + 48).toChar()
        }
        solvedGrid.forEach {
            it.joinToString("").let(::println)
        }
    }
}
