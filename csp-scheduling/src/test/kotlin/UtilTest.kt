import com.tsovedenski.csp.scheduling.*
import org.junit.Assert
import org.junit.Test

/**
 * Created by Ivan Yushchuk on 13/01/19.
 */
class UtilTest {

    @Test
    fun `print Boundary`() {
        val boundary = Boundary(390)
        Assert.assertEquals("Monday 6:30", boundary.print())
    }

    @Test
    fun `parse Boundary`() {
        val boundary = Boundary.fromString("Monday 6:30")
        Assert.assertEquals(390, boundary.minutes)
    }

    @Test
    fun `parse TimeRange`() {
        val timeRange = TimeRange.fromString("Monday 6:20-Monday 6:30")
        Assert.assertEquals(380, timeRange.start.minutes)
        Assert.assertEquals(390, timeRange.end.minutes)
    }

    @Test
    fun `should overlap`() {
        val timeRange1 = TimeRange.fromString("Monday 6:10-Monday 6:30")
        val timeRange2 = TimeRange.fromString("Monday 6:20-Monday 16:30")
        val timeRange3 = TimeRange(Boundary(100), Boundary(300))
        val timeRange4 = TimeRange(Boundary(200), Boundary(400))
        Assert.assertTrue(areOverlapping(timeRange1, timeRange2))
        Assert.assertTrue(areOverlapping(timeRange3, timeRange4))
    }
    @Test
    fun `should not overlap`() {
        val timeRange1 = TimeRange.fromString("Monday 6:20-Monday 6:30")
        val timeRange2 = TimeRange.fromString("Friday 6:20-Friday 6:30")
        val timeRange3 = TimeRange(Boundary(100), Boundary(200))
        val timeRange4 = TimeRange(Boundary(300), Boundary(400))
        Assert.assertTrue(areNotOverlapping(timeRange1, timeRange2))
        Assert.assertTrue(areNotOverlapping(timeRange3, timeRange4))
    }

    @Test
    fun `initialize DayOfWeek`() {
        Assert.assertEquals(DayOfWeek.MON, DayOfWeek.fromString("Monday"))
        Assert.assertNotEquals(DayOfWeek.FRI, DayOfWeek.fromString("Monday"))

        Assert.assertEquals(DayOfWeek.MON, DayOfWeek.fromInt(0))
        Assert.assertNotEquals(DayOfWeek.THU, DayOfWeek.fromInt(1))
    }
}
