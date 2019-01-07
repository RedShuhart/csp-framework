package pruning

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.heuristics.pruning.Slice
import com.tsovedenski.csp.heuristics.pruning.schemas.ForwardChecking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 08/12/18.
 */
class `ForwardChecking prunes domain` {
    private val constraint = BinaryConstraint<Char, Int>('A', 'B') { a, b -> a > b }
    private val assignment = mutableMapOf(
            'A' to Domain.of(1, 2, 3),
            'B' to Domain.of(1, 2, 3)
    )
    private val slice = Slice('A', setOf('B'))

    lateinit var job: Job<Char, Int>

    @Before
    fun setup() {
        job = Job(assignment.toMutableMap(), listOf(constraint))
    }

    @Test fun `when A is 1, B should be empty`()     = test(1, emptyList())
    @Test fun `when A is 2, B should have 1`()       = test(2, listOf(1))
    @Test fun `when A is 3, B should have 1 and 2`() = test(3, listOf(1, 2))

    private fun test(selected: Int, expected: List<Int>) {
        job.assignVariable('A', Selected(selected))
        val pruned = job.prune(slice, ForwardChecking())
        Assert.assertEquals(Domain.of(expected), pruned['B'])
    }
}