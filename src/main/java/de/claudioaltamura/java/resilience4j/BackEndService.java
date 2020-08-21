package de.claudioaltamura.java.resilience4j;

public class BackEndService {

    public String doSomething() {
        throw new RuntimeException("Boom");
    }
}
