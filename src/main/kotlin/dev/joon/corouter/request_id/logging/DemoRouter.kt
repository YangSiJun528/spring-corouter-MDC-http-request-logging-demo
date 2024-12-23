package dev.joon.corouter.request_id.logging

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class DemoRouter {

    @Bean
    fun route(handler: DemoHandler) = coRouter {
        path("/demo").nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/test", handler::get)
            }
        }
    }
}
