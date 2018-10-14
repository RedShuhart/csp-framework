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

//    val `all values are distinct`: Constraint<Char, Int> = { map ->
//        val selecteds = map.filter { it.value is Selected }.values as Collection<Selected<Int>>
//        val vals = selecteds.map(Selected<Int>::value)
//        vals.distinct().size == vals.size
//    }
    val `all values are distinct` = AllDiffConstraint<Char, Int>(variables)

    val `m is non-zero` = UnaryConstraint<Char, Int>('M') { it > 0 }

    val `whole sum holds` = GeneralConstraint<Char, Int> {
        val S = getValue('S')
        val E = getValue('E')
        val N = getValue('N')
        val D = getValue('D')
        val M = getValue('M')
        val O = getValue('O')
        val R = getValue('R')
        val Y = getValue('Y')

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