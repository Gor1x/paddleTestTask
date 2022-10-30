package task

sealed class TaskDependency {
    class OnTask(val task: Task) : TaskDependency()
    class OnFile(val filename: String) : TaskDependency()
}