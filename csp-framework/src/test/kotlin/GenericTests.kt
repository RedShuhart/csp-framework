import com.tsovedenski.csp.*
import com.tsovedenski.csp.strategies.Backtracking
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Tsvetan Ovedenski on 10/01/19.
 */
class GenericTests {

    @Test
    fun `example`() {
        val neq = { x: Int, y: Int -> x != y }
        val problem = Problem(
            constraints = listOf(
                BinaryConstraint('a', 'b', neq),
                BinaryConstraint('b', 'c', neq),
                BinaryConstraint('c', 'd', neq),
                BinaryConstraint('c', 'e', neq),
                BinaryConstraint('d', 'e', neq)
            ),
            domains = mapOf(
                'a' to listOf(1, 2),
                'b' to listOf(1, 2, 3),
                'c' to listOf(1, 2, 3, 4),
                'd' to listOf(1, 2, 3, 4, 5),
                'e' to listOf(1, 2, 3, 4, 5, 6)
            )
        )
        val solution = problem.solve(Backtracking())

        assertTrue(solution is Solved)
        solution as Solved

        val expected = mapOf(
            'a' to 1,
            'b' to 2,
            'c' to 1,
            'd' to 2,
            'e' to 3
        )
        assertEquals(expected, solution.assignment)
    }

    @Test
    fun `example from readme`() {
        val problem = Problem(
                variables = listOf('a', 'b', 'c'),
                domain = listOf(1, 2, 3, 4, 5),
                constraints = listOf(
                        UnaryConstraint('a') { it in 3..4 },
                        BinaryConstraint('a', 'b') { a, b -> a != b },
                        BinaryConstraint('b', 'c') { b, c -> b + c > 4 }
                )
        )

        val solution = problem.solve(Backtracking())

        assertTrue(solution is Solved)
        solution as Solved

        val expected = mapOf(
                'a' to 3,
                'b' to 1,
                'c' to 4
        )
        assertEquals(expected, solution.assignment)
    }

}