package com.tsovedenski.csp.examples.sendmoremoney

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 */
data class WordSum (val a: String, val b: String, val result: String) : Task<Char, Int> {

    override val variables: List<Char> = (result + b + a).toList().distinct()

    override val domain: List<Int> = (0..9).toList()

    override val constraints: List<Constraint<Char, Int>> = listOf(
        AllDiffConstraint(variables),

        UnaryConstraint(result.first()) { it > 0 },

        GeneralConstraint {
            val map = variables.associate { it to getValue(it) }
            val aWord = aReversed.map(map::getValue).zip(tens, ::mult).fold(0, ::sum)
            val bWord = bReversed.map(map::getValue).zip(tens, ::mult).fold(0, ::sum)
            val resultWord = resultReversed.map(::getValue).zip(tens, ::mult).fold(0, ::sum)

            aWord + bWord == resultWord
        }
    )

    private val aReversed = a.reversed().asSequence()
    private val bReversed = b.reversed().asSequence()
    private val resultReversed = result.reversed().asSequence()

    companion object {
        private val tens = generateSequence(1) { it * 10 }

        private fun mult(x: Int, y: Int) = x * y
        private fun sum(x: Int, y: Int) = x + y
    }
}