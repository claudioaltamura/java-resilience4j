import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    id("com.diffplug.spotless") version "6.5.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:1.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

    testImplementation("org.assertj:assertj-core:3.22.0")

    testImplementation("org.mockito:mockito-core:4.5.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform()
	testLogging {
		events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
	}
}