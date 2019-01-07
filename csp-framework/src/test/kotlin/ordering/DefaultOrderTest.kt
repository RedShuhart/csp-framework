package ordering

import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.heuristics.ordering.comparators.DefaultComparator
import com.tsovedenski.csp.heuristics.pruning.Slice
import org.junit.Test
import sliceTest

/**
 * Created by Tsvetan Ovedenski on 09/12/18.
 */
class DefaultOrderTest {
    private val comparator = DefaultComparator<Char, Int>()

    @Test
    fun `when all are choice, first is taken`() = sliceTest(comparator,
        Slice('A', setOf('B', 'C')),

        'A' to Domain.of(1, 2, 3),
        'B' to Domain.of(1, 2, 3),
        'C' to Domain.of(1, 2, 3)
    )

    @Test
    fun `when first is selected, second is taken`() = sliceTest(comparator,
        Slice('B', setOf('C')),

        'A' to Domain.of(1),
        'B' to Domain.of(1, 2, 3),
        'C' to Domain.of(1, 2, 3)
    )

    @Test
    fun `when choice is between two selected, it is taken`() = sliceTest(comparator,
        Slice('B', setOf('D')),

        'A' to Domain.of(1),
        'B' to Domain.of(1, 2, 3),
        'C' to Domain.of(1),
        'D' to Domain.of(1, 2, 3)
    )

    @Test
    fun `when all are selected, current is null and next is empty`() = sliceTest(comparator,
        Slice(null, setOf<Char>()),

        'A' to Domain.of(1),
        'B' to Domain.of(2),
        'C' to Domain.of(3)
    )
}