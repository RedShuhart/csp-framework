package com.tsovedenski.csp.sudoku

import java.io.File

/**
 * Created by Tsvetan Ovedenski on 13/12/18.
 *
 * Loading sudokus from txt
 * http://lipas.uwasa.fi/~timan/sudoku/
 */
object SudokuInstances {
    enum class Difficulty (val level: Int, val list: List<Char>) {
        _1 (1, "abc".toList()),
        _2 (2, "abc".toList()),
        _3 (3, "abc".toList()),
        _4 (4, "abc".toList()),
        _5 (5, "abc".toList()),
        E (6, "abc".toList()),
        C (7, "abc".toList()),
        D (8, "abc".toList()),
        SD (9, "abc".toList()),
        Easy (10, "abc".toList()),
        Medium (11, "abc".toList()),
        Hard (12, "abc".toList()),
        GA_E (13, "abc".toList()),
        GA_M (14, "abc".toList()),
        GA_H (15, "abc".toList()),
        AIEscargot (16, emptyList()),
    }

    fun load(difficulty: Difficulty, which: Int? = null): List<String> {
        val levelName = difficulty.level.toString().padStart(3 - difficulty.level.toString().length, '0')
        val whichName = when (which) {
            null -> difficulty.list.random()
            else -> difficulty.list[which]
        }
        val filename = "/s$levelName$whichName.txt"
        val lines = SudokuInstances::class.java.getResource(filename).readText().split('\n')

        return lines.map { it.replace(" ", "").replace('0', 'x').trim() }.filter(String::isNotBlank)
    }
}