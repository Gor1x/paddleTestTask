package task_graph

class CycleChecker(val waysMap: Map<String, List<String>>) {
    private var hasCyclesResult: Boolean? = null

    fun hasCycles(): Boolean {
        if (hasCyclesResult == null) {
            val visitedStatus = mutableMapOf<String, Status>()
            for (name in waysMap.keys) {
                runDfs(name, visitedStatus)
            }
            if (hasCyclesResult != true) {
                hasCyclesResult = false
            }
        }
        return hasCyclesResult as Boolean
    }

    private fun runDfs(vertexName: String, visitedStatus: MutableMap<String, Status>) {
        if (hasCyclesResult == true) {
            return
        }
        if (visitedStatus[vertexName] == Status.LEFT)
            return
        visitedStatus[vertexName] = Status.IN_VISIT
        for (next in waysMap.getOrDefault(vertexName, listOf())) {
            when (visitedStatus[next]) {
                Status.LEFT -> {
                    // NOOP
                }

                Status.IN_VISIT -> {
                    hasCyclesResult = true
                    return
                }

                else -> {
                    runDfs(next, visitedStatus)
                }
            }
        }
        visitedStatus[vertexName] = Status.LEFT
    }

    private enum class Status {
        IN_VISIT,
        LEFT
    }
}