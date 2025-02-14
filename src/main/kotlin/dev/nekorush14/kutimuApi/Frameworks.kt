package dev.nekorush14.kutimuApi

import dev.nekorush14.kutimuApi.repository.TaskRepository
import dev.nekorush14.kutimuApi.service.TaskService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<HelloService> {
                HelloService {
                    println(environment.log.info("Hello, World!"))
                }
            }
            single { TaskRepository }
            single { TaskService(get()) }
        })
    }
}
