plugins {
    // Apply the java plugin to add support for Java
    java
}

repositories {
    jcenter()
}

dependencies {
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:1.5.0")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")

    testImplementation("org.assertj:assertj-core:3.16.1")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}


val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}
