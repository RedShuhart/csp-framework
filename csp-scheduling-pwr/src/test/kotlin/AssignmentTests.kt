import com.tsovedenski.csp.Domain
import com.tsovedenski.csp.emptyAssignment
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.SingleSubject
import com.tsovedenski.csp.scheduling.pwr.domain.Type.*
import com.tsovedenski.csp.scheduling.pwr.domain.Week.*
import com.tsovedenski.csp.scheduling.pwr.preferences.perDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek.*
/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
class AssignmentTests {

    @Test fun `byDay groups properly`() {
        val s1 = SingleSubject("A", Lecture)
        val g1 = listOf(
            Group("", MONDAY, "11:00-12:00", Even)
        )
        val s2 = SingleSubject("B", Lecture)
        val g2 = listOf(
            Group("", MONDAY, "11:00-12:00", Odd)
        )
        val s3 = SingleSubject("C", Lecture)
        val g3 = listOf(
            Group("", TUESDAY, "11:00-12:00", Both)
        )

        val assignment = emptyAssignment<SingleSubject, Group>().apply {
            this[s1] = Domain.of(g1)
            this[s2] = Domain.of(g2)
            this[s3] = Domain.of(g3)
        }

        val actual = assignment.perDay { it.value }.mapValues { it.value.flatten() }
        val expected = mapOf(
            (MONDAY to Even) to g1,
            (MONDAY to Odd) to g2,
            (TUESDAY to Even) to g3,
            (TUESDAY to Odd) to g3
        )

        assertEquals(expected, actual)
    }
}