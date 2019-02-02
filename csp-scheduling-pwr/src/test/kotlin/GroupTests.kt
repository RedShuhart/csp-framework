import com.tsovedenski.csp.scheduling.pwr.domain.Group
import com.tsovedenski.csp.scheduling.pwr.domain.Week
import org.junit.Assert.*
import org.junit.Test
import java.time.DayOfWeek.*

/**
 * Created by Tsvetan Ovedenski on 2019-02-02.
 */
class GroupTests {

    @Test fun `difference when first is before second`() = differenceTest(
        "11:00-12:00",
        "12:15-13:15",
        15
    )

    @Test fun `difference when first is after second`() = differenceTest(
        "12:15-13:15",
        "11:00-12:00",
        15
    )

    private fun differenceTest(rangeA: String, rangeB: String, expected: Int) {
        val a = Group("", MONDAY, rangeA)
        val b = Group("", MONDAY, rangeB)
        val actual = a.difference(b)
        assertEquals(expected, actual)
    }

    @Test fun `overlaps when A before B`() = overlapsTest(
        "15:15-17:50",
        "17:05-18:45",
        true
    )

    @Test fun `overlaps when Both and Odd week`() = overlapsTest(
        "15:15-17:50", Week.Both,
        "17:05-18:45", Week.Even,
        true
    )

    @Test fun `overlaps when Odd and Both week`() = overlapsTest(
        "17:05-18:45", Week.Even,
        "15:15-17:50", Week.Both,
        true
    )

    private fun overlapsTest(rangeA: String, rangeB: String, expected: Boolean) {
        val a = Group("", MONDAY, rangeA)
        val b = Group("", MONDAY, rangeB)
        val actual = a.overlaps(b)
        assertEquals(expected, actual)
    }

    private fun overlapsTest(rangeA: String, weekA: Week, rangeB: String, weekB: Week, expected: Boolean) {
        val a = Group("", MONDAY, rangeA, weekA)
        val b = Group("", MONDAY, rangeB, weekB)
        val actual = a.overlaps(b)
        assertEquals(expected, actual)
    }
}