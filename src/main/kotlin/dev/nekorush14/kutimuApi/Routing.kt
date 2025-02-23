package dev.nekorush14.kutimuApi

import dev.nekorush14.kutimuApi.entities.Task
import dev.nekorush14.kutimuApi.services.TaskServiceImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val taskServiceImpl by inject<TaskServiceImpl>()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        // GET /tasks
        get ("/tasks") {
            val tasks: List<Task> = taskServiceImpl.getAllTasks()
            call.respond(tasks)
        }

        // GET /tasks/{id}
        get ("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                return@get call.respond("Invalid or missing id")
            }

            val task: Task = taskServiceImpl.getTaskById(id) ?: run {
                return@get call.respond("Task not found")
            }
            call.respond(task)
        }

        // POST /tasks
        post ("/tasks") {
            val task = call.receive<Task>()
            val taskId = taskServiceImpl.createTask(task)
            call.respond(taskId)
        }

        // PUT /tasks/{id}
        put("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                return@put call.respond("Invalid or missing id")
            }

            val task = call.receive<Task>()
            val updatedTask = taskServiceImpl.updateTask(id, task) ?: run {
                return@put call.respond("Task not found")
            }
            call.respond(updatedTask)
        }

        // DELETE /tasks/{id}
        delete("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                return@delete call.respond("Invalid or missing id")
            }

            val isDeleted = taskServiceImpl.deleteTask(id)
            if (isDeleted) {
                call.respond("Task deleted")
            } else {
                call.respond("Task not found")
            }
        }
    }

//    routing {
//        taskRoute()
//    }
}
