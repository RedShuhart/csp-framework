import com.tsovedenski.csp.scheduling.pwr.domain.Course
import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Type
import org.junit.Assert.*
import org.junit.Test
import java.time.DayOfWeek.*

/**
 * Created by Tsvetan Ovedenski on 02/02/19.
 */
class SerializeTests {
    @Test fun `course serializes from to`() {
        val expected = Course("Course name", mapOf(
            Type.Lecture to setOf(Group("Group1", MONDAY, "11:00-12:00")),
            Type.Lab to setOf(
                Group("Group2", TUESDAY, "13:15-14:30"),
                Group("Group3", TUESDAY, "14:15-15:30")
            )
        ))

        val actual = Course.fromString(expected.serialize())

        assertEquals(expected, actual)
    }
}