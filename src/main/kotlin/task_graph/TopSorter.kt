package task_graph

class TopSorter(private val waysMap: Map<String, List<String>>) {
    private var result: MutableList<String>? = null

    fun getTopsort(): MutableList<String> {
        if (result == null) {
            val topsort = mutableListOf<String>()
            val status = mutableMapOf<String, Status>()
            for (name in waysMap.keys) {
                runDfs(name, status, topsort)
            }
            topsort.reverse()
            result = topsort
        }
        return result!!
    }

    private fun runDfs(vertexName: String, status: MutableMap<String, Status>, topSort: MutableList<String>) {
        if (status[vertexName] == Status.VISITED) {
            return
        }
        status[vertexName] = Status.VISITED
        for (next in waysMap.getOrDefault(vertexName, listOf())) {
            runDfs(next, status, topSort)
        }
        topSort.add(vertexName)
    }

    private enum class Status {
        VISITED,
    }


}