package dev.joon.corouter.request_id.logging

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class DemoRouter {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun route(handler: DemoHandler) = coRouter {
        log.info("Initializing router configuration")
        path("/demo").nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/test", handler::get)
            }
        }
    }.also { log.info("Router configuration completed") }
}
