package files


import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import data.TaskData
import data.TaskDataStorage
import java.io.File

class FakeYamlReader {
    private val fakeYaml = File("./fake.yaml")

    private fun YamlNode.parseNodeToYamlTask(): TaskData {
        return Yaml.default.decodeFromString(TaskData.serializer(), this.contentToString())
    }

    fun getContent(): TaskDataStorage {
        val node = Yaml.default.parseToYamlNode(fakeYaml.readText())
        val entries = node.yamlMap.entries.map {
            it.key.content to it.value.parseNodeToYamlTask()
        }
        return TaskDataStorage(entries.toMap())
    }
}