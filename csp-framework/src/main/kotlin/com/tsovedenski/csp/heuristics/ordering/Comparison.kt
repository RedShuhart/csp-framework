package com.tsovedenski.csp.heuristics.ordering

/**
 * Created by Ivan Yushchuk on 01/10/2018.
 */

/**
 * Ascending or descending order.
 */
enum class Order(val asInt: Int) {
    ASC(1), DESC(-1);
}

/**
 * Greater, Less-Than or Equal.
 */
enum class Comparison (val asInt: Int) {
    GT(1),
    LT(-1),
    EQ(0);
    companion object {
        /**
         * Compare two numbers, given some order.
         */
        fun compare(a: Int, b: Int, order: Order = Order.ASC)
                = fromInt(when {
            a < b -> LT
            a > b -> GT
            else  -> EQ
        }.asInt * order.asInt)

        private fun fromInt(key: Int) = values.getValue(key)
        private val values = Comparison.values().associateBy(Comparison::asInt)
    }
}
