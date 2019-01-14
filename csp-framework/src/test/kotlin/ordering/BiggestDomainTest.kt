package ordering

import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.heuristics.ordering.comparators.BiggestDomainVariable
import com.tsovedenski.csp.heuristics.pruning.Slice
import org.junit.Test
import sliceTest

/**
 * Created by Tsvetan Ovedenski on 09/12/18.
 */
class BiggestDomainTest {
    private val comparator = BiggestDomainVariable<Char, Int>()

    @Test
    fun `variable with biggest domain is chosen`() = sliceTest(comparator,
        Slice('B', setOf('C', 'A')),

        'A' to Domain.of(1, 2),
        'B' to Domain.of(1, 2, 3, 4),
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