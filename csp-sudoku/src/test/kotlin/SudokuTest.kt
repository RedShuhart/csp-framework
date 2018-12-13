import com.tsovedenski.csp.*
import com.tsovedenski.csp.sudoku.Sudoku
import com.tsovedenski.csp.heuristics.prouning.schemas.PartialLookAhead
import com.tsovedenski.csp.strategies.Backtracking
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

        val expected = emptyAssignment<String, Int>().apply {
            this["00"] = Selected(value=1)
            this["01"] = Selected(value=2)
            this["02"] = Selected(value=3)
            this["03"] = Selected(value=4)
            this["10"] = Selected(value=3)
            this["11"] = Selected(value=4)
            this["12"] = Selected(value=1)
            this["13"] = Selected(value=2)
            this["20"] = Selected(value=2)
            this["21"] = Selected(value=1)
            this["22"] = Selected(value=4)
            this["23"] = Selected(value=3)
            this["30"] = Selected(value=4)
            this["31"] = Selected(value=3)
            this["32"] = Selected(value=2)
            this["33"] = Selected(value=1)
        }.toCompleteAssignment()!!

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

        val expected = emptyAssignment<String, Int>().apply {
            this["00"] = Selected(value=1)
            this["01"] = Selected(value=8)
            this["05"] = Selected(value=5)
            this["06"] = Selected(value=7)
            this["08"] = Selected(value=6)
            this["11"] = Selected(value=2)
            this["13"] = Selected(value=7)
            this["16"] = Selected(value=3)
            this["17"] = Selected(value=4)
            this["18"] = Selected(value=8)
            this["21"] = Selected(value=6)
            this["22"] = Selected(value=3)
            this["23"] = Selected(value=4)
            this["24"] = Selected(value=2)
            this["25"] = Selected(value=8)
            this["32"] = Selected(value=5)
            this["33"] = Selected(value=2)
            this["34"] = Selected(value=4)
            this["35"] = Selected(value=7)
            this["36"] = Selected(value=9)
            this["41"] = Selected(value=9)
            this["42"] = Selected(value=2)
            this["44"] = Selected(value=8)
            this["46"] = Selected(value=4)
            this["47"] = Selected(value=1)
            this["50"] = Selected(value=3)
            this["52"] = Selected(value=7)
            this["53"] = Selected(value=9)
            this["54"] = Selected(value=1)
            this["58"] = Selected(value=5)
            this["60"] = Selected(value=9)
            this["65"] = Selected(value=2)
            this["66"] = Selected(value=1)
            this["67"] = Selected(value=7)
            this["68"] = Selected(value=4)
            this["70"] = Selected(value=4)
            this["71"] = Selected(value=7)
            this["75"] = Selected(value=9)
            this["77"] = Selected(value=5)
            this["78"] = Selected(value=2)
            this["80"] = Selected(value=2)
            this["82"] = Selected(value=8)
            this["83"] = Selected(value=1)
            this["84"] = Selected(value=7)
            this["85"] = Selected(value=4)
            this["02"] = Selected(value=4)
            this["03"] = Selected(value=3)
            this["04"] = Selected(value=9)
            this["07"] = Selected(value=2)
            this["10"] = Selected(value=5)
            this["12"] = Selected(value=9)
            this["14"] = Selected(value=6)
            this["15"] = Selected(value=1)
            this["20"] = Selected(value=7)
            this["26"] = Selected(value=5)
            this["27"] = Selected(value=9)
            this["28"] = Selected(value=1)
            this["30"] = Selected(value=8)
            this["31"] = Selected(value=1)
            this["37"] = Selected(value=6)
            this["38"] = Selected(value=3)
            this["40"] = Selected(value=6)
            this["43"] = Selected(value=5)
            this["45"] = Selected(value=3)
            this["48"] = Selected(value=7)
            this["51"] = Selected(value=4)
            this["55"] = Selected(value=6)
            this["56"] = Selected(value=2)
            this["57"] = Selected(value=8)
            this["61"] = Selected(value=3)
            this["62"] = Selected(value=6)
            this["63"] = Selected(value=8)
            this["64"] = Selected(value=5)
            this["72"] = Selected(value=1)
            this["73"] = Selected(value=6)
            this["74"] = Selected(value=3)
            this["76"] = Selected(value=8)
            this["81"] = Selected(value=5)
            this["86"] = Selected(value=6)
            this["87"] = Selected(value=3)
            this["88"] = Selected(value=9)
        }.toCompleteAssignment()!!

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

        val expected = emptyAssignment<String, Int>().apply {
            this["03"] = Selected(value=2)
            this["04"] = Selected(value=6)
            this["06"] = Selected(value=7)
            this["08"] = Selected(value=1)
            this["10"] = Selected(value=6)
            this["11"] = Selected(value=8)
            this["14"] = Selected(value=7)
            this["17"] = Selected(value=9)
            this["20"] = Selected(value=1)
            this["21"] = Selected(value=9)
            this["25"] = Selected(value=4)
            this["26"] = Selected(value=5)
            this["30"] = Selected(value=8)
            this["31"] = Selected(value=2)
            this["33"] = Selected(value=1)
            this["37"] = Selected(value=4)
            this["42"] = Selected(value=4)
            this["43"] = Selected(value=6)
            this["45"] = Selected(value=2)
            this["46"] = Selected(value=9)
            this["51"] = Selected(value=5)
            this["55"] = Selected(value=3)
            this["57"] = Selected(value=2)
            this["58"] = Selected(value=8)
            this["62"] = Selected(value=9)
            this["63"] = Selected(value=3)
            this["67"] = Selected(value=7)
            this["68"] = Selected(value=4)
            this["71"] = Selected(value=4)
            this["74"] = Selected(value=5)
            this["77"] = Selected(value=3)
            this["78"] = Selected(value=6)
            this["80"] = Selected(value=7)
            this["82"] = Selected(value=3)
            this["84"] = Selected(value=1)
            this["85"] = Selected(value=8)
            this["00"] = Selected(value=4)
            this["01"] = Selected(value=3)
            this["02"] = Selected(value=5)
            this["05"] = Selected(value=9)
            this["07"] = Selected(value=8)
            this["12"] = Selected(value=2)
            this["13"] = Selected(value=5)
            this["15"] = Selected(value=1)
            this["16"] = Selected(value=4)
            this["18"] = Selected(value=3)
            this["22"] = Selected(value=7)
            this["23"] = Selected(value=8)
            this["24"] = Selected(value=3)
            this["27"] = Selected(value=6)
            this["28"] = Selected(value=2)
            this["32"] = Selected(value=6)
            this["34"] = Selected(value=9)
            this["35"] = Selected(value=5)
            this["36"] = Selected(value=3)
            this["38"] = Selected(value=7)
            this["40"] = Selected(value=3)
            this["41"] = Selected(value=7)
            this["44"] = Selected(value=8)
            this["47"] = Selected(value=1)
            this["48"] = Selected(value=5)
            this["50"] = Selected(value=9)
            this["52"] = Selected(value=1)
            this["53"] = Selected(value=7)
            this["54"] = Selected(value=4)
            this["56"] = Selected(value=6)
            this["60"] = Selected(value=5)
            this["61"] = Selected(value=1)
            this["64"] = Selected(value=2)
            this["65"] = Selected(value=6)
            this["66"] = Selected(value=8)
            this["70"] = Selected(value=2)
            this["72"] = Selected(value=8)
            this["73"] = Selected(value=9)
            this["75"] = Selected(value=7)
            this["76"] = Selected(value=1)
            this["81"] = Selected(value=6)
            this["83"] = Selected(value=4)
            this["86"] = Selected(value=2)
            this["87"] = Selected(value=5)
            this["88"] = Selected(value=9)
        }.toCompleteAssignment()!!

        execute(grid, expected)
    }

    // is too slow for now
//    @Test
//    fun `9x9 hard 1`() {
//        val grid = listOf(
//                "xxx2xxx63",
//                "3xxxx54x1",
//                "xx1xx398x",
//                "xxxxxxx9x",
//                "xxx538xxx",
//                "x3xxxxxxx",
//                "x263xx5xx",
//                "5x37xxxx8",
//                "47xxx1xxx"
//        )
//
//        val expected = emptyAssignment<String, Int>().apply {
//            this["00"] = Selected(value=8)
//            this["01"] = Selected(value=5)
//            this["02"] = Selected(value=4)
//            this["03"] = Selected(value=2)
//            this["04"] = Selected(value=1)
//            this["05"] = Selected(value=9)
//            this["06"] = Selected(value=7)
//            this["07"] = Selected(value=6)
//            this["08"] = Selected(value=3)
//            this["10"] = Selected(value=3)
//            this["11"] = Selected(value=9)
//            this["12"] = Selected(value=7)
//            this["13"] = Selected(value=8)
//            this["14"] = Selected(value=6)
//            this["15"] = Selected(value=5)
//            this["16"] = Selected(value=4)
//            this["17"] = Selected(value=2)
//            this["18"] = Selected(value=1)
//            this["20"] = Selected(value=2)
//            this["21"] = Selected(value=6)
//            this["22"] = Selected(value=1)
//            this["23"] = Selected(value=4)
//            this["24"] = Selected(value=7)
//            this["25"] = Selected(value=3)
//            this["26"] = Selected(value=9)
//            this["27"] = Selected(value=8)
//            this["28"] = Selected(value=5)
//            this["30"] = Selected(value=7)
//            this["31"] = Selected(value=8)
//            this["32"] = Selected(value=5)
//            this["33"] = Selected(value=1)
//            this["34"] = Selected(value=2)
//            this["35"] = Selected(value=6)
//            this["36"] = Selected(value=3)
//            this["37"] = Selected(value=9)
//            this["38"] = Selected(value=4)
//            this["40"] = Selected(value=6)
//            this["41"] = Selected(value=4)
//            this["42"] = Selected(value=9)
//            this["43"] = Selected(value=5)
//            this["44"] = Selected(value=3)
//            this["45"] = Selected(value=8)
//            this["46"] = Selected(value=1)
//            this["47"] = Selected(value=7)
//            this["48"] = Selected(value=2)
//            this["50"] = Selected(value=1)
//            this["51"] = Selected(value=3)
//            this["52"] = Selected(value=2)
//            this["53"] = Selected(value=9)
//            this["54"] = Selected(value=4)
//            this["55"] = Selected(value=7)
//            this["56"] = Selected(value=8)
//            this["57"] = Selected(value=5)
//            this["58"] = Selected(value=6)
//            this["60"] = Selected(value=9)
//            this["61"] = Selected(value=2)
//            this["62"] = Selected(value=6)
//            this["63"] = Selected(value=3)
//            this["64"] = Selected(value=8)
//            this["65"] = Selected(value=4)
//            this["66"] = Selected(value=5)
//            this["67"] = Selected(value=1)
//            this["68"] = Selected(value=7)
//            this["70"] = Selected(value=5)
//            this["71"] = Selected(value=1)
//            this["72"] = Selected(value=3)
//            this["73"] = Selected(value=7)
//            this["74"] = Selected(value=9)
//            this["75"] = Selected(value=2)
//            this["76"] = Selected(value=6)
//            this["77"] = Selected(value=4)
//            this["78"] = Selected(value=8)
//            this["80"] = Selected(value=4)
//            this["81"] = Selected(value=7)
//            this["82"] = Selected(value=8)
//            this["83"] = Selected(value=6)
//            this["84"] = Selected(value=5)
//            this["85"] = Selected(value=1)
//            this["86"] = Selected(value=2)
//            this["87"] = Selected(value=3)
//            this["88"] = Selected(value=9)
//        }.toCompleteAssignment()!!
//
//        execute(grid, expected)
//    }

    private fun execute(grid: List<String>, expected: CompleteAssignment<String, Int>) {
        val task = Sudoku(grid)
        val solution = task.solve(strategy = Backtracking(
            pruneSchema = PartialLookAhead()
        ))

        Assert.assertTrue(solution is Solved)
        solution as Solved

        Assert.assertEquals(expected, solution.assignment)
    }
}