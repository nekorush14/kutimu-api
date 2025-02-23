package dev.nekorush14.kutimuApi

import dev.nekorush14.kutimuApi.repositories.TaskRepository
import dev.nekorush14.kutimuApi.repositories.TaskRepositoryImpl
import dev.nekorush14.kutimuApi.services.TaskService
import dev.nekorush14.kutimuApi.services.TaskServiceImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<TaskRepository> { TaskRepositoryImpl }
            single<TaskService> { TaskServiceImpl(get()) }
        })
    }
}
