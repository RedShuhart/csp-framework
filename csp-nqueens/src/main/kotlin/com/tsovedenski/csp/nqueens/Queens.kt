package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.*
import kotlin.math.abs


/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
class Queens(size: Int) : Solvable<Int, Int> {

    private val n = size / 2
    private val ks = 0 until n
    private val variables = (0 until size).toList()
//    private val variables = ks + ks.map { 2*n+1-it }

//    private val domain = (0 until size).toList()
//    private val domain = (listOf(size / 2) + (0 until size).toList()).distinct()
//    private val domain = ks.map { 1 + (2*(it-1)+n-1) % size } + ks.map { 2*n-(2*(it-1)+n-1)%size }
    private val domain = (size/2 until size) + (0 until size/2)

    private val diags = setOf(-1, 0, 1)

    private val constraints: List<Constraint<Int, Int>> = listOf(
            AllDiffConstraint(variables),

            AllIndexedConstraint(variables) { (i, a), (j, b) ->
                abs(a - b) != abs(i - j)
            }
//            ,
//
//            AllIndexedConstraint(variables) { (i, a), (j, b) ->
//                a + j != i + b && i + a != j + b
//            }
//            ,
//
//            AllIndexedConstraint(variables) { (i, a), (j, b) ->
//                for (m in diags) {
//                    for (n in diags) {
//                        if (i + m == j && a + n == b) {
//                            return@AllIndexedConstraint false
//                        }
//                    }
//                }
//                true
//            }
//            ,
//
//            AllIndexedConstraint(variables) { (i, a), (j, b) ->
//                for (m in -2..2) {
//                    for (n in -2..2) {
//                        if (i + m == j && a + n == b) {
//                            return@AllIndexedConstraint false
//                        }
//                    }
//                }
//                true
//            }
    )

    override fun toProblem(): Problem<Int, Int> = Problem(variables, domain, constraints)
}
