package dev.nekorush14.kutimuApi.repositories

import dev.nekorush14.kutimuApi.models.TaskTable
import dev.nekorush14.kutimuApi.entities.Task
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object TaskRepositoryImpl : TaskRepository {
    override fun createTask(task: Task): Int = transaction {
        TaskTable.insertAndGetId {
            it[this.name] = task.name
            it[this.description] = task.description ?: ""
            it[this.completed] = task.completed
            it[this.icon] = task.icon ?: ""
        }.value
    }

    override fun getAllTasks(): List<Task> = transaction {
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

    override fun findById(id: Int): Task? = transaction {
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

    override fun updateTask(id: Int, task: Task): Int = transaction {
        TaskTable.update({TaskTable.id eq id}) {
            it[this.name] = task.name
            it[this.description] = task.description ?: ""
            it[this.completed] = task.completed
            it[this.icon] = task.icon ?: ""
        }
    }

    override fun deleteTask(id: Int): Int = transaction {
        TaskTable.deleteWhere{ TaskTable.id eq id }
    }
}