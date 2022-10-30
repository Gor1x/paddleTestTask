package data

import kotlinx.serialization.Serializable

@Serializable
data class TaskDataStorage(
    val data: Map<String, TaskData>,
) {
    fun getDependenciesByName(name: String): List<String> {
        return data[name]?.dependencies ?: listOf()
    }

    fun hasTask(name: String): Boolean {
        return data[name] != null
    }


    fun getTask(name: String): TaskData {
        return data[name]!!
    }
}
