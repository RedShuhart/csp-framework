package pruning

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.heuristics.pruning.Slice
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 10/12/18.
 */
class `FullLookAhead prunes both directions` {
    // here A is always second
    private val constraints = listOf(
            BinaryConstraint<Char, Int>('B', 'A') { b, a -> b == a },
            BinaryConstraint<Char, Int>('C', 'B') { c, b -> c == b },
            BinaryConstraint<Char, Int>('C', 'A') { c, a -> c == a }
    )
    private val assignment = mutableMapOf(
            'A' to Domain.of(1, 2, 3),
            'B' to Domain.of(1, 2, 3),
            'C' to Domain.of(1, 2, 3)
    )
    private val slice = Slice('A', setOf('B', 'C'))

    lateinit var job: Job<Char, Int>

    @Before
    fun setup() {
        job = Job(assignment.toMutableMap(), constraints)
    }

    @Test fun `when A is 1, B and C should have 1`() = test(1)
    @Test fun `when A is 2, B and C should have 2`() = test(2)
    @Test fun `when A is 3, B and C should have 3`() = test(3)

    private fun test(selected: Int) {
        job.assignVariable('A', Selected(selected))
        val pruned = job.prune(slice, FullLookAhead())
        Assert.assertEquals("B", Domain.of(selected), pruned['B'])
        Assert.assertEquals("C", Domain.of(selected), pruned['C'])
    }
}