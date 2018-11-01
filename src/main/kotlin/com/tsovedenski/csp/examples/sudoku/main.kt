package com.tsovedenski.csp.examples.sudoku

import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.solve

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
fun main(args: Array<String>) {
    val grid = listOf(
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
//    val grid = listOf(
//        "xxx2xxx63",
//        "3xxxx54x1",
//        "xx1xx398x",
//        "xxxxxxx9x",
//        "xxx538xxx",
//        "x3xxxxxxx",
//        "x263xx5xx",
//        "5x37xxxx8",
//        "47xxx1xxx"
//    )
    val task = Sudoku(grid)
    val solution = task.solve(strategy = Backtracking(variableOrdering = SmallestDomainVariable()))
    solution.print()

    grid.forEach {
        println(it)
    }
    println()
    (solution as? Solved)?.let { solved ->
        val solvedGrid = grid.toMutableList().map { it.toMutableList() }
        solved.assignment.forEach { p, n ->
            val (r, c) = p.toList().map { it.toInt() - 48 }
            solvedGrid[r][c] = ((n as Selected).value + 48).toChar()
        }
        solvedGrid.forEach {
            it.joinToString("").let(::println)
        }
    }
}
