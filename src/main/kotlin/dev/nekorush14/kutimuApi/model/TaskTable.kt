package dev.nekorush14.kutimuApi.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object TaskTable: IntIdTable(name = "task") {
    /** Task name */
    val name: Column<String> = varchar(name = "name", length = 50)
    /** Description for task detail */
    val description: Column<String> = varchar(name = "description", length = 100)
    /** A flag whether task has been completed. */
    val completed: Column<Boolean> = bool(name = "completed")
    /** Icon name related with this task */
    val icon: Column<String> = varchar(name = "icon", length = 200)
}
