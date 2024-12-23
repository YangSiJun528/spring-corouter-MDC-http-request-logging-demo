# Coroutine MDC Request Logging Demo

Spring WebFlux의 coRouter를 사용하는 비동기 환경에서 MDC(Mapped Diagnostic Context)를 활용할 수 있도록 구현한 데모 프로젝트입니다.

코틀린 진영에서 제공하는 MDCContext를 사용해 편리하게 구현 가능합니다.

- 추가 정보
  - WebFlux 환경에서는 더 복잡한 처리가 필요합니다. - [Context Propagation with Project Reactor 3 - Unified Bridging between Reactive and Imperative (Spring Blog)](https://spring.io/blog/2023/03/30/context-propagation-with-project-reactor-3-unified-bridging-between-reactive)
  - `@Async` 같이 다른 스레드에 작업을 위임하는 경우에도 별도의 처리가 필요합니다. - [slf4j MDC(Mapped Diagnostics Context)를 사용하여 로그에 맥락 더하기 (hudi.blog)](https://hudi.blog/slf4j-mapped-diagnotics-context/#%EC%BD%94%EB%A3%A8%ED%8B%B4)

## 프로젝트 소개

이 프로젝트는 다음과 같은 주요 기능을 제공합니다:

- 코루틴 기반의 비동기 HTTP 클라이언트 통신 환경
- HTTP 요청별 고유한 requestId를 MDC에 설정
- Coroutine 컨텍스트를 통한 로깅 컨텍스트 전파

## 핵심 개념

### MDC (Mapped Diagnostic Context)
- 로깅 시스템에서 사용되는 스레드 로컬 컨텍스트
- 각 요청별로 고유한 식별자를 포함하여 로그 추적을 용이하게 함
- Kotlin Coroutine에서는 `MDCContext`를 통해 코루틴 간 전파 가능

### Coroutine Context
- `withContext(MDCContext)`를 사용하여 코루틴 간 MDC 정보 전파
- 비동기 작업 간에도 일관된 로깅 컨텍스트 유지

### WebFlux Functional Router
- 함수형 엔드포인트 정의를 통한 HTTP 요청 처리
- Coroutine 지원을 통한 비동기 처리

## 핵심 클래스

### MDCRequestLoggingFilter
```kotlin
class MDCRequestLoggingFilter : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        val requestId = exchange.request.id
        return withContext(MDCContext(mapOf("requestId" to requestId))) {
            chain.filter(exchange)
        }
    }
}
```
- 모든 HTTP 요청에 대해 requestId를 MDC에 설정
- 후속 처리에서 동일한 requestId로 로깅 가능

### LoopCLIRunner
- 10초마다 주기적으로 실행되는 백그라운드 작업
- HTTP 요청이 이루어지지 않아 MDC 컨텍스트가 없는 상태의 로그를 확인하기 위한 용도

### DemoService & DemoClient
- 외부 API 호출 시나리오 구현
- MDC 컨텍스트가 전파되어 동일한 requestId로 로깅

## 테스트 방법

### API 호출 테스트
```bash
# GET 요청 실행
curl -v http://localhost:8080/demo/test

# 응답 및 서버 로그 확인
# 로그에서 동일한 requestId가 전파되는 것을 확인
```

### 백그라운드 작업 로깅 확인
1. 애플리케이션 실행
2. 로그를 모니터링하여 10초마다 실행되는 로그 확인
3. 백그라운드 작업의 로그에는 requestId가 없음을 확인

## 프로젝트 버전

- Java 17
- Kotlin 1.9.25
- Spring Boot 3.4.1

## 빌드 및 실행

```bash
# 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```
