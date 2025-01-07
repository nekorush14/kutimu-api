package dev.nekorush14.kutimuApi.repository

import dev.nekorush14.kutimuApi.model.TaskTable
import dev.nekorush14.kutimuApi.entity.Task
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TaskRepository {
    /**
     * Register new task.
     *
     * @param name Task name
     * @param description Description for task detail
     * @param completed A flag whether task has been completed
     * @param icon Icon name related with this task
     *
     * @return task id.
     */
    fun create(name: String, description: String, completed: Boolean, icon: String) = transaction {
        TaskTable.insertAndGetId {
            it[this.name] = name
            it[this.description] = description
            it[this.completed] = completed
            it[this.icon] = icon
        }.value
    }

    fun findById(id: Int) = transaction {
        TaskTable.selectAll()
            .where { TaskTable.id eq id}
            .map {
                Task(
                    it[TaskTable.id].value,
                    it[TaskTable.name],
                    it[TaskTable.description],
                    it[TaskTable.icon],
                )
            }
            .singleOrNull()
    }
}