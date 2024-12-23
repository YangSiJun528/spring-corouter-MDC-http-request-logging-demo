package dev.joon.corouter.request_id.logging

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class DemoHandler(
    private val demoService: DemoService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun get(request: ServerRequest): ServerResponse {
        log.info("handler call get()")
        val data = demoService.get()
        val response = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(data)
        return response
    }
}
