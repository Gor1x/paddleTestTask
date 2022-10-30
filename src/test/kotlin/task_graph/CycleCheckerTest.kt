package task_graph

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CycleCheckerTest {
    @Test
    fun `Test graph with cycle`() {
        // Arrange
        val ways = mutableMapOf(
            "A" to listOf("B", "C"),
            "C" to listOf("D", "E"),
            "E" to listOf("A")
        )
        // Assert
        assertTrue(CycleChecker(ways).hasCycles())
    }

    @Test
    fun `Test graph with no cycle`() {
        // Arrange
        val ways = mutableMapOf(
            "A" to listOf("B", "C"),
            "C" to listOf("D", "E"),
            "E" to listOf("D")
        )
        // Assert
        assertFalse(CycleChecker(ways).hasCycles())
    }
}