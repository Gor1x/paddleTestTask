package data

import kotlinx.serialization.Serializable

@Serializable
data class TaskData(
    val dependencies: List<String>?,
    val target: String,
    val run: String,
)