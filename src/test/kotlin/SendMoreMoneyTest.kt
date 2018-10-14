import com.tsovedenski.csp.Selected
import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.emptyAssignment
import com.tsovedenski.csp.examples.sendmoremoney.createTask
import com.tsovedenski.csp.solve
import org.junit.Assert
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 14/10/18.
 */
class SendMoreMoneyTest {

    @Test
    fun `send + more = money`() {
        val task = createTask()
        val solution = solve(task)

        Assert.assertNotNull(solution)
        solution as Solved

        Assert.assertEquals(21_741_206, solution.statistics.counter)

        val expectedAssignment = emptyAssignment<Char, Int>().apply {
            put('M', Selected(1))
            put('S', Selected(9))
            put('E', Selected(5))
            put('N', Selected(6))
            put('D', Selected(7))
            put('O', Selected(0))
            put('R', Selected(8))
            put('Y', Selected(2))
        }
        Assert.assertEquals(expectedAssignment, solution.assignment)
    }
}