import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.nqueens.Queens
import com.tsovedenski.csp.strategies.Backtracking
import org.junit.Assert
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 19/10/18.
 */
class QueensTest {
    @Test
    fun `1 queens`() {
        val expected = emptyAssignment<Int, Int>().apply {
            this[0] = Selected(value = 0)
        }.toCompleteAssignment()!!

        executeTest(1, expected)
    }

    @Test
    fun `2 queens no solution`() {
        executeTestNegative(2)
    }

    @Test
    fun `3 queens no solution`() {
        executeTestNegative(3)
    }

    @Test
    fun `4 queens`() {
        val expected = emptyAssignment<Int, Int>().apply {
            this[0] = Selected(value = 1)
            this[1] = Selected(value = 3)
            this[2] = Selected(value = 0)
            this[3] = Selected(value = 2)
        }.toCompleteAssignment()!!

        executeTest(4, expected)
    }

    @Test
    fun `5 queens`() {
        val expected = emptyAssignment<Int, Int>().apply {
            this[0] = Selected(value = 0)
            this[1] = Selected(value = 2)
            this[2] = Selected(value = 4)
            this[3] = Selected(value = 1)
            this[4] = Selected(value = 3)
        }.toCompleteAssignment()!!

        executeTest(5, expected)
    }

    @Test
    fun `8 queens`() {
        val expected = emptyAssignment<Int, Int>().apply {
            this[0] = Selected(value = 0)
            this[1] = Selected(value = 4)
            this[2] = Selected(value = 7)
            this[3] = Selected(value = 5)
            this[4] = Selected(value = 2)
            this[5] = Selected(value = 6)
            this[6] = Selected(value = 1)
            this[7] = Selected(value = 3)
        }.toCompleteAssignment()!!

        executeTest(8, expected)
    }

    private fun executeTest(n: Int, expected: CompleteAssignment<Int, Int>) {
        val task = Queens(n)
        val solution = task.solve(strategy = Backtracking(
                pruneSchema = PartialLookAhead()
        ))

        Assert.assertTrue(solution is Solved)
        solution as Solved

        Assert.assertEquals(expected, solution.assignment)
    }

    private fun executeTestNegative(n: Int) {
        val task = Queens(n)
        val solution = task.solve(strategy = Backtracking())

        Assert.assertTrue(solution is NoSolution)
    }
}
