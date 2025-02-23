package dev.nekorush14.kutimuApi.services

import dev.nekorush14.kutimuApi.entities.Task
import dev.nekorush14.kutimuApi.exceptions.KutimuException
import dev.nekorush14.kutimuApi.models.TaskTable
import dev.nekorush14.kutimuApi.repositories.TaskRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class TaskServiceImplTest: FunSpec({
    val taskRepository = mockk<TaskRepository>()
    lateinit var taskService: TaskServiceImpl

    beforeTest { taskService = TaskServiceImpl(taskRepository) }

    beforeSpec {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.create(TaskTable)
        }
    }

    val task1 = Task(
        id = 1,
        name = "Task 1",
        description = "Description 1",
        completed = false,
        icon = "icon1"
    )
    val task2 = Task(
        id = 2,
        name = "Task 2",
        description = "Description 2",
        completed = true,
        icon = "icon2")

    context("getAllTasks") {
        test("should return all tasks from repository") {
            // Given
            every { taskRepository.getAllTasks() } returns listOf(task1, task2)

            // When
            val tasks = taskService.getAllTasks()

            // Then
            tasks shouldBe listOf(task1, task2)
        }
    }

    context("getTaskById") {
        test("should return task from repository when task exists") {
            // Given
            every { taskRepository.findById(1) } returns task1

            // When
            val task = taskService.getTaskById(1)

            // Then
            task shouldBe task1
        }

        test("should return null when task does not exist") {
            // Given
            every { taskRepository.findById(3) } returns null

            // When
            val task = taskService.getTaskById(3)

            // Then
            task shouldBe null
        }
    }

    context("createTask") {
        test("should return task id from repository") {
            // Given
            every { taskRepository.createTask(task1) } returns 1

            // When
            val taskId = taskService.createTask(task1)

            // Then
            taskId shouldBe 1
        }
    }

    context("updateTask") {
        test("should return updated task when task exists") {
            // Given
            every { taskRepository.updateTask(1, task2) } returns 1
            every { taskRepository.findById(1) } returns task2

            // When
            val updatedTask = taskService.updateTask(1, task2)

            // Then
            updatedTask shouldBe task2
        }

        test("should throw exception when task which will be updated is not found") {
            // Given
            val taskId = 3
            val task3 = Task(
                id = 3,
                name = "Task 3",
                description = "Description 3",
                completed = false,
                icon = "icon3"
            )

            // When
            val actualException = shouldThrow<KutimuException> {
                taskService.updateTask(taskId, task3)
            }

            // Then
            actualException.message shouldBe "Failed to update task. Task id: $taskId"
        }
    }

    context("deleteTask") {
        test("should return true when task is deleted") {
            // Given
            every { taskRepository.deleteTask(1) } returns 1

            // When
            val isDeleted = taskService.deleteTask(1)

            // Then
            isDeleted shouldBe true
        }

        test("should throw exception when task which will be deleted is not found") {
            // Given
            val taskId = 3

            // When
            val actualException = shouldThrow<KutimuException> {
                taskService.deleteTask(taskId)
            }

            // Then
            actualException.message shouldBe "Failed to delete task. Task id: $taskId"
        }
    }
})