package endterm.controller

import endterm.model.Task;
import endterm.service.TaskService;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/manage")
class TaskController(private val taskService: TaskService) {

    // Получение всех задач
    @GetMapping("/tasks")
    fun getAllTasks(): List<Task> = taskService.getAllTasks()

    // Получение задачи по ID
    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<Task> {
        val task = taskService.getTaskById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(task)
    }

    // Создание новой задачи
    @PostMapping("/tasks")
    fun createTask(@RequestBody task: Task): ResponseEntity<Task> {
        val newTask = taskService.createTask(task)
        return ResponseEntity(newTask, HttpStatus.CREATED)
    }

    // Обновление задачи
    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody updatedTask: Task): ResponseEntity<Task> {
        val taskToUpdate = taskService.updateTask(id, updatedTask) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(taskToUpdate)
    }

    // Удаление задачи
    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Void> {
        if (!taskService.deleteTask(id)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}