package dev.nekorush14.kutimuApi.repository

import dev.nekorush14.kutimuApi.model.TaskTable
import dev.nekorush14.kutimuApi.entity.Task
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object TaskRepository {
    /**
     * Register new task.
     *
     * @param task A task information for creating to task.
     *
     * @return task id.
     */
    fun createTask(task: Task): Int = transaction {
        TaskTable.insertAndGetId {
            it[this.name] = task.name
            it[this.description] = task.description
            it[this.completed] = task.completed
            it[this.icon] = task.icon
        }.value
    }

    /**
     * Retrieve all tasks.
     *
     * @return All tasks.
     */
    fun getAllTasks(): List<Task> = transaction {
        TaskTable.selectAll()
            .map {
                Task(
                    it[TaskTable.id].value,
                    it[TaskTable.name],
                    it[TaskTable.description],
                    it[TaskTable.completed],
                    it[TaskTable.icon],
                )
            }
    }

    /**
     * Retrieve specified task by task id.
     *
     * @param id Search target task id
     *
     * @return Searched task.
     */
    fun findById(id: Int): Task? = transaction {
        TaskTable.selectAll()
            .where { TaskTable.id eq id}
            .map {
                Task(
                    it[TaskTable.id].value,
                    it[TaskTable.name],
                    it[TaskTable.description],
                    it[TaskTable.completed],
                    it[TaskTable.icon],
                )
            }
            .singleOrNull()
    }

    /**
     * Update specified task by task id.
     *
     * @param id Update target task id
     * @param task A task information for updating task.
     *
     * @return Updated task id.
     */
    fun updateTask(id: Int, task: Task): Int = transaction {
        TaskTable.update({TaskTable.id eq id}) {
            it[this.name] = task.name
            it[this.description] = task.description
            it[this.completed] = task.completed
            it[this.icon] = task.icon
        }
    }

    /**
     * Delete specified task by task id.
     *
     * @param id Delete target task id
     *
     * @return Count of deleted rows.
     */
    fun deleteTask(id: Int): Int = transaction {
        TaskTable.deleteWhere{ TaskTable.id eq id }
    }
}