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
class `ForwardChecking changes next variable only` {
    private val constraints = listOf(
        BinaryConstraint<Char, Int>('A', 'B') { a, b -> a > b },
        BinaryConstraint<Char, Int>('B', 'C') { b, c -> b > c },
        BinaryConstraint<Char, Int>('A', 'C') { a, c -> a > c }
    )
    private val assignment = mutableMapOf(
            'A' to Domain.of(1, 2, 3),
            'B' to Domain.of(1, 2, 3),
            'C' to Domain.of(1, 2, 3)
    )
    private val slice = Slice('A', setOf('B'))

    lateinit var job: Job<Char, Int>

    @Before
    fun setup() {
        job = Job(assignment.toMutableMap(), constraints)
    }

    @Test fun `when A is 1, C should be unchanged`() = test(1)
    @Test fun `when A is 2, C should be unchanged`() = test(2)
    @Test fun `when A is 3, C should be unchanged`() = test(3)

    private fun test(selected: Int) {
        job.assignVariable('A', Selected(selected))
        val pruned = job.prune(slice, ForwardChecking())
        Assert.assertEquals(assignment['C'], pruned['C'])
    }
}