package de.claudioaltamura.java.resilience4j;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.SupplierUtils;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class CircuitBreakerTest {

    private final BackEndService backEndService = new BackEndService();

    @Test void recoverFromExceptionBeforeCircuitBreakerFailure() {

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("default");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, backEndService::doSomething);

        Supplier<String> supplierWithRecovery = SupplierUtils
                .recover(decoratedSupplier, (exception) -> "Hello Recovery");

        String result = circuitBreaker.executeSupplier(supplierWithRecovery);

        assertThat(result).isEqualTo("Hello Recovery");
    }


    @Test void customCircuitBreakerConfig() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .minimumNumberOfCalls(6)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreaker circuitBreakerWithCustomConfig = circuitBreakerRegistry
                .circuitBreaker("custom", circuitBreakerConfig);

        circuitBreakerWithCustomConfig.getEventPublisher().onError(event -> System.out.println(event));

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreakerWithCustomConfig, backEndService::doSomething);

        Supplier<String> supplierWithRecovery = SupplierUtils
                .recover(decoratedSupplier, (exception) -> "Hello Recovery");

        int i = 0;
        try {
            for(; i < 10; i++) {
                circuitBreakerWithCustomConfig.executeSupplier(supplierWithRecovery);
            }
        } catch(CallNotPermittedException e) {
          //nothing
        }

        assertThat(i).isEqualTo(3); // slidingWindowSize 5, 4

    }
}
