package dev.joon.corouterrequest_idlogging

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CorouterRequestIdLoggingApplication

fun main(args: Array<String>) {
    runApplication<CorouterRequestIdLoggingApplication>(*args)
}
