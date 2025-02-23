package dev.nekorush14.kutimuApi.services

import dev.nekorush14.kutimuApi.entities.Task

/**
 * Task service interface.
 * This interface is used to define the methods that the task service should implement.
 */
interface TaskService {
    /**
     * Retrieve all tasks.
     *
     * @return All tasks.
     */
    fun getAllTasks(): List<Task>

    /**
     * Retrieve specified task by task id.
     *
     * @param id Search target task id
     * @return Searched task.
     */
    fun getTaskById(id: Int): Task?

    /**
     * Register new task.
     *
     * @param task A task information for creating to task.
     * @return task id.
     */
    fun createTask(task: Task): Int

    /**
     * Update specified task by task id.
     *
     * @param id Update target task id
     * @param task A task information for updating task.
     * @return Updated task.
     */
    fun updateTask(id: Int, task: Task): Task?

    /**
     * Delete specified task by task id.
     *
     * @param id Delete target task id
     * @return Is deleted task or not.
     */
    fun deleteTask(id: Int): Boolean
}