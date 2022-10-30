package task_graph

import data.TaskDataStorage

class GraphGetter(private val taskDataStorage: TaskDataStorage) {
    fun getGraph(names: Iterable<String>): Map<String, List<String>> {
        val reverseEdges = mutableMapOf<String, MutableList<String>>()
        for (name in names) {
            val deps = taskDataStorage.getDependenciesByName(name)
            deps.forEach {
                val list = reverseEdges.getOrDefault(it, mutableListOf())
                list.add(name)
                reverseEdges[it] = list
            }
        }
        return reverseEdges
    }
}