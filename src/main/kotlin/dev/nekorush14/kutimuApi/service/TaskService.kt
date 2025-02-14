package dev.nekorush14.kutimuApi.service

import dev.nekorush14.kutimuApi.entity.Task
import dev.nekorush14.kutimuApi.repository.TaskRepository

class TaskService(private val taskRepository: TaskRepository) {

    /**
     * Retrieve all tasks.
     *
     * @return All tasks.
     */
    fun getAllTasks(): List<Task> {
        return taskRepository.getAllTasks()
    }

    /**
     * Retrieve specified task by task id.
     *
     * @param id Search target task id
     *
     * @return Searched task.
     */
    fun getTaskById(id: Int): Task? {
        return taskRepository.findById(id)
    }

    /**
     * Register new task.
     *
     * @param task A task information for creating to task.
     *
     * @return task id.
     */
    fun createTask(task: Task): Int {
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
    fun updateTask(id: Int, task: Task): Task? {
        val updatedId = taskRepository.updateTask(
            id = id,
            task = task,
        )
        return TaskRepository.findById(id = updatedId)
    }

    /**
     * Delete specified task by task id.
     *
     * @param id Search target task id
     *
     * @return true if task is deleted, false if task is not found.
     */
    fun deleteTask(id: Int): Boolean {
        val deletedRowCount = taskRepository.deleteTask(id = id)
        return deletedRowCount > 0
    }
}