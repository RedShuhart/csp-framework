package com.tsovedenski.csp.examples.sudoku

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 15/10/2018.
 *
 * listOf(
 *      "12xx5xxx9",
 *      "x3x9xx4x8",
 *      ...
 * )
 */
data class Sudoku (val grid: List<String>, val placeholder: Char = 'x') : Task<String, Int>() {

    override val variables: List<String> = (0..8).toList().pairs().map { (x, y) -> "$x$y" }

    override val domain: List<Int> = (1..9).toList()

    override val constraints: List<Constraint<String, Int>> = listOf(
        differentRows,
        differentCols
    )

    override val initialAssignment: Map<String, Int> = grid
            .asSequence()
            .zip(generateSequence(0) { it + 1 })
            .flatMap { (row, ir) -> row.asSequence().mapIndexed { ic, c -> Triple(c, ir, ic) } }
            .filter { (c, _, _) -> c != placeholder }
            .associate { (c, ir, ic) -> "$ir$ic" to (c.toInt() - 48) }

    companion object {
        // [[00, 01,.., 08], [10, 11,.., 18], ...]
        private val differentRows = AllDiffConstraint<String, Int>((0..8).flatMap { r -> (0..8).map { c -> "$r$c" } })

        // [[00, 10,.., 80], [01, 11,.., 81], ...]
        private val differentCols = AllDiffConstraint<String, Int>((0..8).flatMap { r -> (0..8).map { c -> "$c$r" } })
    }
}