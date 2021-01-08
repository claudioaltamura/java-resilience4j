import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the java plugin to add support for Java
    java
	id ("com.github.ben-manes.versions") version "0.36.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:1.6.1")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")

    testImplementation("org.assertj:assertj-core:3.18.1")

    testImplementation("org.mockito:mockito-core:3.6.28")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}


val test by tasks.getting(Test::class) {
	useJUnitPlatform()
	testLogging {
		events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
	}
}