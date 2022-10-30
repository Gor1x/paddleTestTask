package task

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.getLastModifiedTime

class Task(
    private val name: String,
    private val dependencies: List<TaskDependency>,
    private val target: String,
    private val command: String,
) {
    fun executeRun() {
        if (isUpToDate()) {
            println("$name| IS UP TO DATE")
        } else {
            dependencies.filter { !it.isUpToDate() }.filterIsInstance<TaskDependency.OnTask>().forEach {
                it.task.executeRun()
            }
            val out = command.runCommand(File("./"))
            if (out != null) {
                println(out)
            }
        }
    }

    fun String.runCommand(workingDir: File): String? {
        return try {
            val parts = this.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

            proc.waitFor(60, TimeUnit.MINUTES)
            proc.inputStream.bufferedReader().readText()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun isUpToDate(): Boolean = isFakeYamlUpToDate() && dependencies.all { it.isUpToDate() }

    private fun isFakeYamlUpToDate(): Boolean {
        val file = Path("./fake.yaml")
        val target = Path(target)
        return file.exists() && target.exists() && file.getLastModifiedTime() <= target.getLastModifiedTime()
    }

    private fun TaskDependency.isUpToDate(): Boolean {
        return when (this) {
            is TaskDependency.OnFile -> {
                val file = Path(this.filename)
                val target = Path(target)
                file.exists() && target.exists() && file.getLastModifiedTime() <= target.getLastModifiedTime()
            }

            is TaskDependency.OnTask -> this.task.isUpToDate()
        }
    }
}