package dev.joon.corouter.request_id.logging

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication
class CorouterRequestIdLoggingApplication

fun main(args: Array<String>) {
    runApplication<CorouterRequestIdLoggingApplication>(*args)
}
