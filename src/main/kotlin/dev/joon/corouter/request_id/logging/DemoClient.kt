package dev.joon.corouter.request_id.logging

import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class DemoClient {
    private val log = LoggerFactory.getLogger(javaClass)
    private val webClient = WebClient.builder().build()

    suspend fun getTodo(id: Int): String {
        log.info("Fetching todo with id: $id")
        return webClient.get()
            .uri("https://jsonplaceholder.typicode.com/todos/{id}", id)
            .retrieve()
            .bodyToMono<String>()
            .awaitSingle()
            .also { log.info("Received response: $it") }
    }
}
