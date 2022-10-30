import files.FakeYamlReader
import task.TaskStorage

fun main(args: Array<String>) {
    val taskStorage = TaskStorage(FakeYamlReader().getContent())
    val taskName = args[0]
    val task = taskStorage.getTask(taskName)

    task.executeRun()
}