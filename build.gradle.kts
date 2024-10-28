import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val voyagerVersion: String by project
val exposedVersion: String by project
val koinVersion: String by project
val sqliteVersion: String by project
val flywayVersion: String by project
val hikariVersion: String by project
val sl4jVersion: String by project
val logbackVersion: String by project
val coroutineVersion: String by project
val jacksonVersion: String by project
val jwtVersion: String by project
val bcryptVersion: String by project
val oneTimeVersion: String by project
val zxingVersion: String by project
val toastVersion: String by project
val richEditorVersion: String by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "shade.dev.local"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)
    implementation(compose.material3)

    implementation("cafe.adriel.voyager", "voyager-navigator", voyagerVersion)
    implementation("cafe.adriel.voyager", "voyager-transitions", voyagerVersion)
    implementation("cafe.adriel.voyager", "voyager-screenmodel", voyagerVersion)
    implementation("cafe.adriel.voyager", "voyager-koin", voyagerVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-swing", coroutineVersion)

    implementation("io.insert-koin", "koin-core", koinVersion)
    implementation("io.insert-koin", "koin-compose", koinVersion)
    implementation("io.insert-koin", "koin-logger-slf4j", koinVersion)

    implementation("org.flywaydb", "flyway-core", flywayVersion)
    implementation("org.xerial", "sqlite-jdbc", sqliteVersion)
    implementation("com.zaxxer", "HikariCP", hikariVersion)

    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)

    implementation("com.fasterxml.jackson.core", "jackson-databind", jacksonVersion)
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin", jacksonVersion)
    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", jacksonVersion)

    implementation("com.auth0", "java-jwt", jwtVersion)
    implementation("org.mindrot", "jbcrypt", bcryptVersion)
    implementation("com.atlassian", "onetime", oneTimeVersion)
    implementation("com.google.zxing", "core", zxingVersion)
    implementation("com.google.zxing", "javase", zxingVersion)

    implementation("io.github.dokar3", "sonner", toastVersion)
    implementation("com.mohamedrejeb.richeditor", "richeditor-compose", richEditorVersion)

    implementation("org.slf4j", "slf4j-api", sl4jVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SecVault"
            packageVersion = "1.0.0"
        }
    }
}
