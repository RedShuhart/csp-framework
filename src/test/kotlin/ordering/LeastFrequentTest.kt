package ordering

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.GeneralConstraint
import com.tsovedenski.csp.UnaryConstraint
import com.tsovedenski.csp.heuristics.ordering.comparators.LeastFamousVariable
import com.tsovedenski.csp.heuristics.prouning.Slice
import const
import const2
import org.junit.Test
import sliceTest

/**
 * Created by Tsvetan Ovedenski on 09/12/18.
 */
class LeastFrequentTest {
    private val comparator = LeastFamousVariable<Char, Int>()

    @Test
    fun `variable used in least constraints is chosen`() = sliceTest(comparator,
        // A is used 4 times, B - 1 time, C - 2 times
        listOf(
            UnaryConstraint('A', const(true)),
            BinaryConstraint('A', 'B', const2(true)),
            BinaryConstraint('A', 'C', const2(true)),
            GeneralConstraint(listOf('A', 'C'), const(true))
        ),

        Slice('B', setOf('C', 'A')),

        'C' to Domain.of(1, 2, 3),
        'B' to Domain.of(1, 2, 3),
        'A' to Domain.of(1, 2, 3)
    )

    @Test
    fun `variables with same usage chosen in order`() = sliceTest(comparator,
        listOf(
            UnaryConstraint('A', const(true)),
            UnaryConstraint('B', const(true)),
            UnaryConstraint('C', const(true))
        ),

        Slice('A', setOf('B', 'C')),

        'A' to Domain.of(1, 2),
        'B' to Domain.of(1, 2, 3, 4),
        'C' to Domain.of(1, 2, 3)
    )
}