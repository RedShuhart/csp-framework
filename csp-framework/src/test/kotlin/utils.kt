import com.tsovedenski.csp.Assignment
import com.tsovedenski.csp.Constraint
import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.Job
import com.tsovedenski.csp.heuristics.ordering.comparators.VariableComparator
import com.tsovedenski.csp.heuristics.pruning.Slice
import org.junit.Assert.assertEquals

/**
 * Created by Tsvetan Ovedenski on 09/12/18.
 */
fun <V, D> createJob(assignment: Assignment<V, D>): Job<V, D>
    = Job(assignment, emptyList())

fun <V, D> createJob(vararg assignment: Pair<V, Domain<D>>): Job<V, D>
    = createJob(assignment.toMap().toMutableMap())

fun <V, D> sliceTest(
    comparator: VariableComparator<V, D>,
    constraints: List<Constraint<V, D>>,
    expected: Slice<V>,
    vararg assignment: Pair<V, Domain<D>>
) {
    val job = Job(assignment.toMap().toMutableMap(), constraints)
    val slice = job.sliceAtCurrent(comparator)
    assertEquals(expected, slice)
}

fun <V, D> sliceTest(
    comparator: VariableComparator<V, D>,
    expected: Slice<V>,
    vararg assignment: Pair<V, Domain<D>>
)
    = sliceTest(comparator, emptyList(), expected, *assignment)

fun <A, B> const(value: A): (B) -> A = { value }
fun <A, B, C> const2(value: A): (B, C) -> A = { _, _ -> value }