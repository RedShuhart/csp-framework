import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.strategies.Backtracking
import com.tsovedenski.csp.sudoku.Sudoku
import org.junit.Assert
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 18/10/18.
 */
class SudokuTest {

    @Test
    fun `4x4 empty`() {
        val grid = listOf(
                "xxxx",
                "xxxx",
                "xxxx",
                "xxxx"
        )

        val expected = listOf(
                "1234",
                "3412",
                "2143",
                "4321"
        )

        execute(grid, expected)
    }

    @Test
    fun `9x9 easy 1`() {
        val grid = listOf(
                "18xxx57x6",
                "x2x7xx348",
                "x63428xxx",
                "xx52479xx",
                "x92x8x41x",
                "3x791xxx5",
                "9xxxx2174",
                "47xxx9x52",
                "2x8174xxx"
        )

        val expected = listOf(
                "184395726",
                "529761348",
                "763428591",
                "815247963",
                "692583417",
                "347916285",
                "936852174",
                "471639852",
                "258174639"
        )

        execute(grid, expected)
    }

    @Test
    fun `9x9 easy 2`() {
        val grid = listOf(
                "xxx26x7x1",
                "68xx7xx9x",
                "19xxx45xx",
                "82x1xxx4x",
                "xx46x29xx",
                "x5xxx3x28",
                "xx93xxx74",
                "x4xx5xx36",
                "7x3x18xxx"
        )

        val expected = listOf(
                "435269781",
                "682571493",
                "197834562",
                "826195347",
                "374682915",
                "951743628",
                "519326874",
                "248957136",
                "763418259"
        )

        execute(grid, expected)
    }

    @Test
    fun `9x9 hard 1`() {
        val grid = listOf(
                "xxx2xxx63",
                "3xxxx54x1",
                "xx1xx398x",
                "xxxxxxx9x",
                "xxx538xxx",
                "x3xxxxxxx",
                "x263xx5xx",
                "5x37xxxx8",
                "47xxx1xxx"
        )

        val expected = listOf(
                "854219763",
                "397865421",
                "261473985",
                "785126394",
                "649538172",
                "132947856",
                "926384517",
                "513792648",
                "478651239")

        execute(grid, expected)
    }

    private fun execute(grid: List<String>, expected: CompleteAssignment<String, Int>) {
        val task = Sudoku(grid)
        val solution = task.solve(strategy = Backtracking(
            pruneSchema = PartialLookAhead()
        ))

        Assert.assertTrue(solution is Solved)
        solution as Solved

        Assert.assertEquals(expected, solution.assignment)
    }

    private fun execute(grid: List<String>, expected: List<String>) {
        val expectedAssignment = expected
                .mapIndexed { r, row ->
                    row.mapIndexed { c, cell -> "$r$c" to "$cell".toInt() }
                }.flatten().toMap()
        execute(grid, expectedAssignment)
    }
}