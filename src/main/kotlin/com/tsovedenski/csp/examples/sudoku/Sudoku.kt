package com.tsovedenski.csp.examples.sudoku

import com.tsovedenski.csp.*
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 *
 * listOf(
 *      "12xx5xxx9",
 *      "x3x9xx4x8",
 *      ...
 * )
 */
data class Sudoku (val grid: List<String>, val placeholder: Char = 'x') : Solvable<String, Int> {

    override fun toTask(): Task<String, Int> {
        val domains = variables.withDomain(domain).toMutableMap()
        known.forEach { c, v -> domains.merge(c, v) { _, x -> x} }
        return Task(domains, constraints)
    }

    private val size = grid.size

    private val variables = (0 until size)
            .flatMap { x -> (0 until size).map { y -> "$x$y" } }

    private val domain = (1..9).toList()

    private val constraints: List<Constraint<String, Int>> = listOf(
            rows(size).map<List<String>, Constraint<String, Int>>(::AllDiffConstraint),
            cols(size).map<List<String>, Constraint<String, Int>>(::AllDiffConstraint),
            blocks(size).map<List<String>, Constraint<String, Int>>(::AllDiffConstraint)
    ).flatten()

    private val known: Map<String, List<Int>> = grid
            .asSequence()
            .zip(generateSequence(0) { it + 1 })
            .flatMap { (row, ir) -> row.asSequence().mapIndexed { ic, c -> Triple(c, ir, ic) } }
            .filter { (c, _, _) -> c != placeholder }
            .associate { (c, ir, ic) -> "$ir$ic" to listOf(c.toInt() - 48) }

    companion object {
        // [[00, 01,.., 08], [10, 11,.., 18], ...]
        private val rows: (Int) -> List<List<String>> = { size -> (0 until size).map { r -> (0 until size).map { c -> "$r$c" } } }

        // [[00, 10,.., 80], [01, 11,.., 81], ...]
        private val cols: (Int) -> List<List<String>> = { size -> (0 until size).map { r -> (0 until size).map { c -> "$c$r" } } }

        // [[00, 01, 02, 10, 11, 12, 20, 21, 22], ...]
        private val blocks: (Int) -> List<List<String>> = { size ->
            val sq = sqrt(size.toDouble()).roundToInt()
            val ns = (0 until size).asSequence()
            ns
                    .flatMap { a -> ns.map { b -> a to b }.windowed(sq, size) }
                    .windowed(sq, sq)
                    .map { it.flatten() }
                    .flatMap { ls ->
                        (0 until size step sq)
                                .asSequence()
                                .map { i ->
                                    ls
                                            .map { (a, b) -> a to b + i }
                                            .map { (r, c) -> "$r$c" }
                                }
                    }
                    .toList()
        }
    }
}
