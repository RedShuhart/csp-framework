package ordering

import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.heuristics.ordering.comparators.SmallestDomainVariable
import com.tsovedenski.csp.heuristics.pruning.Slice
import org.junit.Test
import sliceTest

/**
 * Created by Tsvetan Ovedenski on 09/12/18.
 */
class SmallestDomainTest {
    private val comparator = SmallestDomainVariable<Char, Int>()

    @Test
    fun `variable with smallest domain is chosen`() = sliceTest(comparator,
        Slice('A', setOf('C', 'B')),

        'B' to Domain.of(1, 2, 3, 4),
        'A' to Domain.of(1, 2),
        'C' to Domain.of(1, 2, 3)
    )

    @Test
    fun `same-length domains chosen in order`() = sliceTest(comparator,
        Slice('A', setOf('B', 'C')),

        'A' to Domain.of(1, 2, 3),
        'B' to Domain.of(1, 2, 3),
        'C' to Domain.of(1, 2, 3)
    )
}