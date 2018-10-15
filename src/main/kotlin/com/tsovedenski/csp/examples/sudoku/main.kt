package com.tsovedenski.csp.examples.sudoku

import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.solve

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
fun main(args: Array<String>) {
    // something is wrong, doesn't solve quickly enough
    val grid = listOf(
            "xxx26x7x1",
            "68xx7xx9x",
            "19xxx45xx",
            "82x1xxx4x",
            "xx46x29xx",
            "x5xxx3x28",
            "xx93xxx74",
            "x4xx5xx36",
            "7x3x18xxx"
    )
    val task = Sudoku(grid)
    val solution = task.solve(strategy = Backtracking)
    solution.print()

    (solution as? Solved)?.let { solved ->
        val solvedGrid = grid.toMutableList().map { it.toMutableList() }
        solved.assignment.forEach { p, n ->
            val r = p.first().toInt()
            val c = p.drop(1).first().toInt()
            solvedGrid[r][c] = ((n as Selected).value + 48).toChar()
        }
        solvedGrid.forEach {
            it.joinToString("").let(::println)
        }
    }
}