package endterm.service

import endterm.model.Task;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

import java.util.concurrent.ConcurrentHashMap

@Service
class TaskService {

    private val tasks = ConcurrentHashMap<Long, Task>()
    private var idCounter = 1L

    fun getAllTasks(): List<Task> = tasks.values.toList()

    fun getTaskById(id: Long): Task? = tasks[id]

    fun createTask(task: Task): Task {
        task.id = idCounter++
        tasks[task.id!!] = task
        return task
    }

    fun updateTask(id: Long, updatedTask: Task): Task? {
        val existingTask = tasks[id] ?: return null
        existingTask.title = updatedTask.title
        existingTask.description = updatedTask.description
        existingTask.completed = updatedTask.completed
        return existingTask
    }

    fun deleteTask(id: Long): Boolean = tasks.remove(id) != null
}