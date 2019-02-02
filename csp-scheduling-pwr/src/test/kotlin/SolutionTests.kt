import com.tsovedenski.csp.Solved
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.scheduling.pwr.Schedule
import com.tsovedenski.csp.scheduling.pwr.asString
import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.preferences.ClassesFor
import com.tsovedenski.csp.scheduling.pwr.preferences.DailyClasses
import com.tsovedenski.csp.scheduling.pwr.preferences.MinutesBetweenClasses
import com.tsovedenski.csp.solve
import com.tsovedenski.csp.strategies.Backtracking
import org.junit.Assert.*
import org.junit.Test
import java.time.DayOfWeek.*

/**
 * Created by Tsvetan Ovedenski on 02/02/19.
 */
class SolutionTests {

    @Test
    fun `solves properly`() {
        val courses = loadFile("courses.txt")
            .split("\n")
            .filter(String::isNotBlank)
            .map(Course.Companion::fromString)

        val problem = Schedule(
            courses, DailyClasses.atMost(3)
            , ClassesFor(FRIDAY).atMost(2)
            , MinutesBetweenClasses.atLeast(10)
        )
        val solution = problem.solve(Backtracking(pruneSchema = FullLookAhead()))

        assertTrue(solution is Solved)
        solution as Solved

        val actual = solution.assignment.asString()
        val expected = loadFile("courses_solved.txt")

        assertEquals(expected, actual)
    }

    private fun loadFile(filename: String) = javaClass.classLoader
        .getResource(filename).readText()
}