import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.8"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.32"
    kotlin("plugin.spring") version "1.5.32"
    kotlin("plugin.jpa") version "1.5.32"
    `maven-publish`
}

group = "com.todolist"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.0")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.5.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.0")
    implementation("me.paulschwarz:spring-dotenv:2.4.1")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("io.kotest:kotest-assertions-core:4.6.3")

    testImplementation("org.testcontainers:testcontainers:1.16.2")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.2"))
    testImplementation("org.testcontainers:mariadb")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty(
            "junit.jupiter.execution.parallel.enabled",
            System.getProperty("junit.jupiter.execution.parallel.enabled") ?: "false"
    )
    jvmArgs("-Dspring.profiles.active=test")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/boloagegit/todolist-backend")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}