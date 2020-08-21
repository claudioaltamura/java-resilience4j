package de.claudioaltamura.java.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.SupplierUtils;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class CircuitBreakerTest {

    @Test void recoverFromExceptionBeforeCircuitBreakerFailure() {
        BackEndService backEndService = new BackEndService();
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("default");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, backEndService::doSomething);

        Supplier<String> supplierWithRecovery = SupplierUtils
                .recover(decoratedSupplier, (exception) -> "Hello Recovery");

        String result = circuitBreaker.executeSupplier(supplierWithRecovery);

        assertThat(result).isEqualTo("Hello Recovery");
    }
}
