package com.tsovedenski.csp.examples.sendmoremoney

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
data class WordSum (val a: String, val b: String, val result: String) : Solvable<Char, Int> {

    private val variables =  (result + b + a).toList().distinct()

    private val domain = (0..9).toList()

    private val constraints: List<Constraint<Char, Int>> = listOf(
        UnaryConstraint(result.first()) { it > 0 },

        UnaryConstraint(a.first()) { it > 0 },

        UnaryConstraint(b.first()) { it > 0 },

        AllDiffConstraint(variables.toList()),

        GeneralConstraint(variables.toList()) {
            val map = variables.toList().associate { it to getValue(it) }
            val aWord = aReversed.map(map::getValue).zip(tens, ::mult).sum()
            val bWord = bReversed.map(map::getValue).zip(tens, ::mult).sum()
            val resultWord = resultReversed.map(::getValue).zip(tens, ::mult).sum()

            aWord + bWord == resultWord
        }
    )

    private val aReversed = a.reversed().asSequence()
    private val bReversed = b.reversed().asSequence()
    private val resultReversed = result.reversed().asSequence()

    override fun toProblem(): Problem<Char, Int> = Problem(variables, domain, constraints)

    companion object {
        private val tens = generateSequence(1) { it * 10 }

        private fun mult(x: Int, y: Int) = x * y
        private fun sum(x: Int, y: Int) = x + y
    }
}
