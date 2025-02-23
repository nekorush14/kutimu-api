package dev.nekorush14.kutimuApi.repositories

import dev.nekorush14.kutimuApi.entities.Task

/**
 * Task repository interface.
 * This interface is used to define the methods that the task repository should implement.
 */
interface TaskRepository {
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
        fun findById(id: Int): Task?

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
         * @return Updated task id.
         */
        fun updateTask(id: Int, task: Task): Int

        /**
         * Delete specified task by task id.
         *
         * @param id Delete target task id
         * @return Deleted task id.
         */
        fun deleteTask(id: Int): Int
}