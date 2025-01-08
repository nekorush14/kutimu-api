package dev.nekorush14.kutimuApi.service

import dev.nekorush14.kutimuApi.entity.Task
import dev.nekorush14.kutimuApi.repository.TaskRepository

class TaskService(private val taskRepository: TaskRepository) {

    suspend fun getTaskById(id: Int): Task? {
        return taskRepository.findById(id)
    }

    suspend fun createTask(task: Task): Int {
        return taskRepository.createTask(task = task)
    }

    suspend fun updateTask(id: Int, task: Task): Task? {
        val updatedId = taskRepository.updateTask(
            id = id,
            task = task,
        )
        return taskRepository.findById(id = id)
    }

    suspend fun deleteTask(id: Int): Boolean {
        val deletedRowCount = taskRepository.deleteTask(id = id)
        return deletedRowCount > 0
    }
}