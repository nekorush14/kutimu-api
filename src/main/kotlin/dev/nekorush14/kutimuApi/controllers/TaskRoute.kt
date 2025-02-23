package dev.nekorush14.kutimuApi.controllers

import dev.nekorush14.kutimuApi.entities.Task
import dev.nekorush14.kutimuApi.services.TaskService
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Route.taskRoute() {
    val taskService by inject<TaskService>()

    // GET /tasks
    get ("/tasks") {
        val tasks: List<Task> = taskService.getAllTasks()
        call.respond(tasks)
    }

    // GET /tasks/{id}
    get ("/tasks/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: run {
            return@get call.respond("Invalid or missing id")
        }

        val task: Task = taskService.getTaskById(id) ?: run {
            return@get call.respond("Task not found")
        }
        call.respond(task)
    }

    // POST /tasks
    post ("/tasks") {
        val task = call.receive<Task>()
        val taskId = taskService.createTask(task)
        call.respond(taskId)
    }

    // PUT /tasks/{id}
    put("/tasks/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: run {
            return@put call.respond("Invalid or missing id")
        }

        val task = call.receive<Task>()
        val updatedTask = taskService.updateTask(id, task) ?: run {
            return@put call.respond("Task not found")
        }
        call.respond(updatedTask)
    }

    // DELETE /tasks/{id}
    delete("/tasks/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: run {
            return@delete call.respond("Invalid or missing id")
        }

        val isDeleted = taskService.deleteTask(id)
        if (isDeleted) {
            call.respond("Task deleted")
        } else {
            call.respond("Task not found")
        }
    }
}