package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.valueOf

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
private val EMPTY = "◦ "
private val QUEEN = "♛ "
fun printQueens(solution: Solved<Int, Int>) {
    val size = solution.assignment.size

    repeat(size) { i ->
        val string = MutableList(size) { EMPTY }
        val value = solution.assignment.valueOf(size - 1 - i)
        string[value] = QUEEN
        println(prefix(i) + string.joinToString(""))
    }
    println("  ╚${"═".repeat(size * 2)}")
    println("    ${(0 until size).joinToString(" ")}")
}

private fun prefix(i: Int) = "$i ╟ "