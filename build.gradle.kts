val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    kotlin("plugin.serialization") version "1.4.21"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation ("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation ("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation ("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation ("org.jetbrains.exposed:exposed-java-time:0.40.1")
    implementation ("org.mongodb:mongodb-driver-sync:4.7.2")
    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    implementation("io.ktor:ktor-server-conditional-headers-jvm:2.1.3")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.1.3")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.5.21")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}