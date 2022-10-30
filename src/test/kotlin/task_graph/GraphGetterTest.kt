package task_graph

import data.TaskData
import data.TaskDataStorage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GraphGetterTest {
    @Test
    fun `Test simple graph builder`() {
        val task1 = TaskData(listOf("main.c", "task2"), DUMMY, DUMMY)
        val task2 = TaskData(listOf("main.c"), DUMMY, DUMMY)

        val storage = TaskDataStorage(
            mapOf(
                "task1" to task1,
                "task2" to task2
            )
        )

        val graph = GraphGetter(storage).getGraph(listOf("task1", "task2"))
        assertEquals(
            graph, mapOf(
                "main.c" to listOf("task1", "task2"),
                "task2" to listOf("task1")
            )
        )
    }


    @Test
    fun `Test simple graph builder with not all names`() {
        val task1 = TaskData(listOf("main.c", "task2"), DUMMY, DUMMY)
        val task2 = TaskData(listOf("main.c"), DUMMY, DUMMY)

        val storage = TaskDataStorage(
            mapOf(
                "task1" to task1,
                "task2" to task2
            )
        )

        val graph = GraphGetter(storage).getGraph(listOf("task1"))
        assertEquals(
            graph, mapOf(
                "main.c" to listOf("task1"),
                "task2" to listOf("task1")
            )
        )
    }

    companion object {
        const val DUMMY = "dummy"
    }
}