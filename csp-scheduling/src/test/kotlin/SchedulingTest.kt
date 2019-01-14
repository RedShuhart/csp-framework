import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.pruning.schemas.PartialLookAhead
import com.tsovedenski.csp.scheduling.*
import com.tsovedenski.csp.strategies.Backtracking
import org.junit.Assert
import org.junit.Test

/**
 * Created by Ivan Yushchuk on 13/01/19.
 */
class SchedulingTest {
    @Test
    fun `should find solution from file`() {
        val filePath = SchedulingTest::class.java.getResource("has-solution-test.txt").path
        val schedules = readFileToSchedules(filePath)
        executeTest(schedules)
    }

    @Test
    fun `should find solution from Map`() {
        executeTest(validSchedules)
    }

    @Test
    fun `should not find solution from file`() {
        val filePath = SchedulingTest::class.java.getResource("has-no-solution-test.txt").path
        val schedules = readFileToSchedules(filePath)
        executeTestNegative(schedules)
    }

    @Test
    fun `should not find solution from Map`() {
        executeTestNegative(invalidSchedules)
    }

    private fun executeTest(schedule: Map<String, List<TimeRange>>) {
        val task = Scheduling(schedule)
        val solution = task.solve(strategy = Backtracking(
                pruneSchema = PartialLookAhead()
        ))
        Assert.assertTrue(solution is Solved)
    }

    private fun executeTestNegative(schedule: Map<String, List<TimeRange>>) {
        val task = Scheduling(schedule)
        val solution = task.solve(strategy = Backtracking(
                pruneSchema = PartialLookAhead()
        ))
        Assert.assertTrue(solution is NoSolution)
    }

    private val validSchedules = mapOf(
            "Algebra" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00"), generateTimeRange(DayOfWeek.MON, "2:30", "3:30")),
            "Calculus" to listOf(generateTimeRange(DayOfWeek.TUE, "1:00", "2:00"), generateTimeRange(DayOfWeek.TUE, "2:30", "3:30")),
            "Physics" to listOf(generateTimeRange(DayOfWeek.WED, "1:00", "2:00"), generateTimeRange(DayOfWeek.WED, "2:30", "3:30")),
            "German" to listOf(generateTimeRange(DayOfWeek.THU, "1:00", "2:00"), generateTimeRange(DayOfWeek.THU, "2:30", "3:30")),
            "Geography" to listOf(generateTimeRange(DayOfWeek.FRI, "1:00", "2:00"), generateTimeRange(DayOfWeek.FRI, "2:30", "3:30")),
            "History" to listOf(generateTimeRange(DayOfWeek.SAT, "1:00", "2:00"), generateTimeRange(DayOfWeek.SAT, "2:30", "3:30")),
            "Literature" to listOf(generateTimeRange(DayOfWeek.SUN, "1:00", "2:00"), generateTimeRange(DayOfWeek.SUN, "2:30", "3:30"))
    )

    private val invalidSchedules = mapOf(
            "Algebra" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00")),
            "Calculus" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00")),
            "Physics" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00")),
            "German" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00")),
            "Geography" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00")),
            "History" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00")),
            "Literature" to listOf(generateTimeRange(DayOfWeek.MON, "1:00", "2:00"))
    )
}
