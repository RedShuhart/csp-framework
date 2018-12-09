import com.tsovedenski.csp.Assignment
import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.heuristics.ordering.comparators.DefaultComparator
import com.tsovedenski.csp.heuristics.prouning.Slice
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 09/12/18.
 */
class SliceTest {

    @Test
    fun `when all are choice, first is taken`() {
        val job = createJob(
            "ABC".toList()
                 .associateWith { Domain.of(1, 2, 3) }
                 .toMutableMap()
        )

        val slice = job.sliceAtCurrent()

        val expected = Slice('A', setOf('B', 'C'))
        assertEquals(expected, slice)
    }

    @Test
    fun `when first is selected, second is taken`() {
        val job = createJob(
            'A' to Domain.of(1),
            'B' to Domain.of(1, 2, 3),
            'C' to Domain.of(1, 2, 3)
        )

        val slice = job.sliceAtCurrent()

        val expected = Slice('B', setOf('C'))
        assertEquals(expected, slice)
    }

    @Test
    fun `when choice is between two selected, it is taken`() {
        val job = createJob(
            'A' to Domain.of(1),
            'B' to Domain.of(1, 2, 3),
            'C' to Domain.of(1),
            'D' to Domain.of(1, 2, 3)
        )

        val slice = job.sliceAtCurrent()

        val expected = Slice('B', setOf('D'))
        assertEquals(expected, slice)
    }

    @Test
    fun `when all are selected, current is null and next is empty`() {
        val job = createJob(
            'A' to Domain.of(1),
            'B' to Domain.of(2),
            'C' to Domain.of(3)
        )

        val slice = job.sliceAtCurrent()

        val expected = Slice(null, setOf())
        assertEquals(expected, slice)
    }

    private fun <V, D> createJob(assignment: Assignment<V, D>): Job<V, D> = Job(assignment, emptyList())
    private fun <V, D> createJob(vararg assignment: Pair<V, Domain<D>>): Job<V, D> = createJob(assignment.toMap().toMutableMap())

    private fun <V, D> Job<V, D>.sliceAtCurrent() = sliceAtCurrent(DefaultComparator())
}