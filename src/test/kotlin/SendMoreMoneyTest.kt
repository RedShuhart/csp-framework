import com.tsovedenski.csp.*
import com.tsovedenski.csp.examples.sendmoremoney.WordSum
import com.tsovedenski.csp.strategies.Backtracking
import org.junit.Assert
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 14/10/18.
 */
class SendMoreMoneyTest {

    @Test
    fun `send + more = money`() {
        val task = WordSum("SEND", "MORE", "MONEY")
        val solution = task.solve(strategy = Backtracking)

        Assert.assertNotNull(solution)
        solution as Solved

//        Assert.assertEquals(21_741_206, solution.statistics.counter)
//        Assert.assertEquals(11_836_646, solution.statistics.counter)

        val expectedAssignment = emptyAssignment<Char, Int>().apply {
            put('M', Selected(1))
            put('O', Selected(0))
            put('N', Selected(6))
            put('E', Selected(5))
            put('Y', Selected(2))
            put('S', Selected(9))
            put('D', Selected(7))
            put('R', Selected(8))
        }
        Assert.assertEquals(expectedAssignment, solution.assignment)
    }
}