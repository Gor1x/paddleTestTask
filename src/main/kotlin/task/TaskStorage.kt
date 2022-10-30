package task

import data.TaskDataStorage
import files.NoSuchTaskException
import files.TasksHasCyclesException
import task_graph.CycleChecker
import task_graph.GraphGetter
import task_graph.TopSorter

class TaskStorage(private val taskDataStorage: TaskDataStorage) {
    private val tasks: MutableMap<String, Task> = mutableMapOf()

    init {
        val topsort = getTopsortedNames()

        topsort.filter { !isFileName(it) }.forEach { vertexName ->
            val taskData = taskDataStorage.getTask(vertexName)
            val dependencies = (taskData.dependencies ?: listOf()).map {
                if (taskDataStorage.hasTask(it)) {
                    TaskDependency.OnTask(tasks[it]!!)
                } else {
                    TaskDependency.OnFile(it)
                }
            }
            tasks[vertexName] = Task(vertexName, dependencies, taskData.target, taskData.run)
        }
    }

    private fun isFileName(name: String): Boolean {
        return !taskDataStorage.hasTask(name)
    }

    private fun getTopsortedNames(): List<String> {
        val reversedEdges = GraphGetter(taskDataStorage).getGraph(taskDataStorage.data.keys)
        if (CycleChecker(reversedEdges).hasCycles()) {
            throw TasksHasCyclesException()
        }
        return TopSorter(reversedEdges).getTopsort()
    }

    fun getTask(name: String): Task {
        return tasks[name] ?: throw NoSuchTaskException()
    }
}