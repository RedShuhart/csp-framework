package pruning

import com.tsovedenski.csp.BinaryConstraint
import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.heuristics.prouning.Slice
import com.tsovedenski.csp.heuristics.prouning.schemas.ForwardChecking
import com.tsovedenski.csp.heuristics.prouning.schemas.FullLookAhead
import com.tsovedenski.csp.heuristics.prouning.schemas.PartialLookAhead
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 08/12/18.
 */
class `FullLookAhead prunes domains` {
    private val constraints = listOf(
            BinaryConstraint<Char, Int>('A', 'B') { a, b -> a > b },
            BinaryConstraint<Char, Int>('B', 'C') { b, c -> b > c },
            BinaryConstraint<Char, Int>('A', 'C') { a, c -> a < c }
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

    @Test fun `when A is 1, B and C should be empty`() = test(1, emptyList(), emptyList())
    @Test fun `when A is 2, B and C should be empty`() = test(2, emptyList(), emptyList())
    @Test fun `when A is 3, B and C should be empty`() = test(3, emptyList(), emptyList())

    private fun test(selected: Int, expectedB: List<Int>, expectedC: List<Int>) {
        job.assignVariable('A', Selected(selected))
        val pruned = job.prune(slice, FullLookAhead())
        Assert.assertEquals("B", Domain.of(expectedB), pruned['B'])
        Assert.assertEquals("C", Domain.of(expectedC), pruned['C'])
    }
}