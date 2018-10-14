@file:Suppress("UNCHECKED_CAST")

package com.tsovedenski.csp.examples.sendmoremoney

import com.tsovedenski.csp.*

/**
 * Created by Tsvetan Ovedenski on 13/10/18.
 *
 *
 *       S E N D
 *   +   M O R E
 *   = M O N E Y
 */
fun main(args: Array<String>) {
    val variables = "MSENDMOREMONEY".toList().distinct()
    val domain = (0..9).toList()

    val `all values are distinct`: Constraint<Char, Int> = { map ->
        val selecteds = map.filter { it.value is Selected }.values as Collection<Selected<Int>>
        val vals = selecteds.map(Selected<Int>::value)
        vals.distinct().size == vals.size
    }

    val `m is non-zero`: Constraint<Char, Int> = fn@{ map ->
        val M = map.valueOf('M') ?: return@fn false
        M > 0
    }

//    example of bad constraint
//    val `right-most holds`: Constraint = fn@{ map ->
//        val D = map.valueOf('D') ?: return@fn false
//        val E = map.valueOf('E') ?: return@fn false
//        val Y = map.valueOf('Y') ?: return@fn false
//        D + E == Y
//    }

    val `whole sum holds`: Constraint<Char, Int> = fn@{ map ->
        val S = map.valueOf('S') ?: return@fn false
        val E = map.valueOf('E') ?: return@fn false
        val N = map.valueOf('N') ?: return@fn false
        val D = map.valueOf('D') ?: return@fn false
        val M = map.valueOf('M') ?: return@fn false
        val O = map.valueOf('O') ?: return@fn false
        val R = map.valueOf('R') ?: return@fn false
        val Y = map.valueOf('Y') ?: return@fn false

        val SEND  =            1000*S + 100*E + 10*N + 1*D
        val MORE  =            1000*M + 100*O + 10*R + 1*E
        val MONEY = 10_000*M + 1000*O + 100*N + 10*E + 1*Y

        SEND + MORE == MONEY
    }

    val task = Task(
            variables   = variables,
            domain      = domain,
            constraints = listOf(
                `m is non-zero`,
                `all values are distinct`,
                `whole sum holds`
            )
    )

    val solution = solve(task)
    solution.print()
}