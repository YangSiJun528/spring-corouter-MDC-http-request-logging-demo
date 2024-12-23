package dev.joon.corouter.request_id.logging

import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.server.*

@Component
class MDCRequestLoggingFilter : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        val requestId = exchange.request.id

        return withContext(MDCContext(mapOf("requestId" to requestId))) {
            chain.filter(exchange)
        }
    }
}
