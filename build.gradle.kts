import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("plugin.jpa") version "1.6.0"
}

group = "com.producement"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("org.postgresql:postgresql")
	implementation("com.kreait.slack:slack-spring-boot-starter:1.0.0")
	implementation("org.jsoup:jsoup:1.14.3")
	implementation("io.github.microutils:kotlin-logging:2.1.23")
	testImplementation("com.kreait.slack:slack-spring-test-api-client:1.0.0")
	testImplementation("com.kreait.slack:slack-jackson-dto-test:1.0.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.testcontainers:postgresql:1.16.2")
	testImplementation("org.mock-server:mockserver-netty:5.11.2")
	testImplementation("org.mock-server:mockserver-client-java:5.11.2")
	testImplementation("org.mock-server:mockserver-junit-jupiter:5.11.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
