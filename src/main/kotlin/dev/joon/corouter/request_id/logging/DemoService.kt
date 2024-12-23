package dev.joon.corouter.request_id.logging

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DemoService(
    private val demoClient: DemoClient
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun get(): String {
        log.info("Fetching todo with id [1,2]")
        val todo1 = demoClient.getTodo(1)
        val todo2 = demoClient.getTodo(2)
        val todo = "[$todo1, $todo2]"
        return todo
    }
}
