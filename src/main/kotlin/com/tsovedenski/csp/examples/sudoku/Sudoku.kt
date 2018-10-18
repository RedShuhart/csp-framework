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

    val size = grid.size

    override val variables: List<String> = (0 until size).flatMap { x -> (0 until size).map { y -> "$x$y" } }

    override val domain: List<Int> = (1..9).toList()

    override val constraints: List<Constraint<String, Int>> =
            rows(size).map<List<String>, Constraint<String, Int>>(::AllDiffConstraint) + cols(size).map(::AllDiffConstraint)
//            rows(size).map<List<String>, Constraint<String, Int>>(::AllDiffConstraint) +
//            listOf(
//                    AllDiffConstraint("00", "10", "20", "30", "40", "50", "60", "70", "80")
//            ) // adding 04 halts the csp

    private val known: Map<String, Selected<Int>> = grid
            .asSequence()
            .zip(generateSequence(0) { it + 1 })
            .flatMap { (row, ir) -> row.asSequence().mapIndexed { ic, c -> Triple(c, ir, ic) } }
            .filter { (c, _, _) -> c != placeholder }
            .associate { (c, ir, ic) -> "$ir$ic" to Selected(c.toInt() - 48) }

    override val initialAssignment: Map<String, Variable<Int>> = //known
            variables.associate { it to Choice(domain) }
                    .toMutableMap<String, Variable<Int>>()
                    .apply {
                        known.forEach { cell, selected ->
                            this[cell] = selected
                            val (r, c) = cell.toList().map { it.toInt() - 48 }
                            (0..8).forEach { i ->
                                val pr = "$r$i"
                                val pc = "$i$c"
                                val vr = this[pr] as? Choice
                                if (vr != null) {
                                    val filtered = vr.values.filter { it != selected.value }
                                    this[pr] = if (filtered.size == 1) Selected(filtered.first())
                                                else Choice(filtered)
                                }
                                val vc = this[pc] as? Choice
                                if (vc != null) {
                                    val filtered = vc.values.filter { it != selected.value }
                                    this[pc] = if (filtered.size == 1) Selected(filtered.first())
                                                else Choice(filtered)
                                }
                            }
                        }
                    }
// .apply {
////                        put("04", Choice((1..9).toList()))
//                    }

    companion object {
        // [[00, 01,.., 08], [10, 11,.., 18], ...]
        private val rows: (Int) -> List<List<String>> = { size -> (0 until size).map { r -> (0 until size).map { c -> "$r$c" } } }

        // [[00, 10,.., 80], [01, 11,.., 81], ...]
        private val cols: (Int) -> List<List<String>> = { size -> (0 until size).map { r -> (0 until size).map { c -> "$c$r" } } }
    }
}