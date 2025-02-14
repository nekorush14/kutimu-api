package dev.nekorush14.kutimuApi.controller

import dev.nekorush14.kutimuApi.entity.Task
import dev.nekorush14.kutimuApi.service.TaskService
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Route.taskRoute() {
    val taskService by inject<TaskService>()

    route("/task") {
        // GET /task
        get {
            val tasks: List<Task> = taskService.getAllTasks()
            call.respond(tasks)
        }

        // GET /task/{id}
        get ("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                return@get call.respond("Invalid or missing id")
            }

            val task: Task = taskService.getTaskById(id) ?: run {
                return@get call.respond("Task not found")
            }
            call.respond(task)
        }

        // POST /task
        post {
            val task = call.receive<Task>()
            val taskId = taskService.createTask(task)
            call.respond(taskId)
        }

        // PUT /task/{id}
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                return@put call.respond("Invalid or missing id")
            }

            val task = call.receive<Task>()
            val updatedTask = taskService.updateTask(id, task) ?: run {
                return@put call.respond("Task not found")
            }
            call.respond(updatedTask)
        }

        // DELETE /task/{id}
        delete("/{id}") {
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
}