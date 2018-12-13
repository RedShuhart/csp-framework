package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
private val EMPTY = "◦ "
private val QUEEN = "♛ "
private val ids = (0..9).map(Int::toString) + ('A'..'Z')
fun printQueens(solution: Solved<Int, Int>) {
    val size = solution.assignment.size

    repeat(size) { i ->
        val string = MutableList(size) { EMPTY }
        val value = solution.assignment.getValue(size - 1 - i)
        string[value] = QUEEN
        println(prefix(i) + string.joinToString(""))
    }
    println("  ╚${"═".repeat(size * 2)}")
    println("    ${ids.take(size).joinToString(" ")}")
}

fun printQueensPartial(assignment: Assignment<Int, Int>) {
    val size = assignment.size

    repeat(size) { i ->
        val string = MutableList(size) { EMPTY }
        val variables = assignment.variablesOf(size - 1 - i)
        if (variables.size == 1) {
            string[variables.first()] = QUEEN
        }
        println(prefix(i) + string.joinToString(""))
    }
    println("  ╚${"═".repeat(size * 2)}")
    println("    ${(0 until size).joinToString(" ")}")
}

private fun prefix(i: Int) = "${ids[i]} ╟ "
