package dev.nekorush14.kutimuApi.services

import dev.nekorush14.kutimuApi.entities.Task
import dev.nekorush14.kutimuApi.exceptions.KutimuException
import dev.nekorush14.kutimuApi.repositories.TaskRepository
import io.github.oshai.kotlinlogging.KotlinLogging

class TaskServiceImpl(
    private val taskRepository: TaskRepository
): TaskService {

    private val logger = KotlinLogging.logger {}

    /**
     * Retrieve all tasks.
     *
     * @return All tasks.
     */
    override fun getAllTasks(): List<Task> {
        return taskRepository.getAllTasks()
    }

    /**
     * Retrieve specified task by task id.
     *
     * @param id Search target task id
     *
     * @return Searched task.
     */
    override fun getTaskById(id: Int): Task? {
        return taskRepository.findById(id)
    }

    /**
     * Register new task.
     *
     * @param task A task information for creating to task.
     *
     * @return task id.
     */
    override fun createTask(task: Task): Int {
        return taskRepository.createTask(task = task)
    }

    /**
     * Update specified task by task id.
     *
     * @param id Search target task id
     * @param task A task information for updating to task.
     *
     * @return Updated task.
     */
    override fun updateTask(id: Int, task: Task): Task? {
        try {
            val updatedId = taskRepository.updateTask(
                id = id,
                task = task,
            )
            return taskRepository.findById(id = updatedId)
        } catch (e: Exception) {
            val errorMessage = "Failed to update task. Task id: $id"
            logger.error { errorMessage }
            logger.error { e.stackTraceToString() }
            throw KutimuException(errorMessage)
        }
    }

    /**
     * Delete specified task by task id.
     *
     * @param id Search target task id
     *
     * @return true if task is deleted, false if task is not found.
     */
    override fun deleteTask(id: Int): Boolean {
        try {
            val deletedRowCount = taskRepository.deleteTask(id = id)
            return deletedRowCount > 0
        } catch (e: Exception) {
            val errorMessage = "Failed to delete task. Task id: $id"
            logger.error { errorMessage }
            logger.error { e.stackTraceToString() }
            throw KutimuException(errorMessage)
        }
    }
}