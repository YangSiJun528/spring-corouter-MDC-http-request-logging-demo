package dev.joon.corouter.request_id.logging

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class LoopCLIRunner : CommandLineRunner, DisposableBean {
    private val log = LoggerFactory.getLogger(javaClass)

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun run(vararg args: String?) {
        scope.launch {
            while (true) {
                val currentTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
                log.info("Current time: $currentTime")
                delay(10_000)
            }
        }
    }

    override fun destroy() {
        job.cancel() // 모든 코루틴 정리
    }
}
