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
val googleApiClient: String by project
val googleOAuthClient: String by project
val googleDriveApi: String by project
val googleAuthLibrary: String by project
val googlePersonApi: String by project
val apachePoiApi: String by project

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
    implementation("com.google.api-client", "google-api-client", googleApiClient)
    implementation("com.google.oauth-client", "google-oauth-client-jetty", googleOAuthClient)
    implementation("com.google.apis", "google-api-services-drive", googleDriveApi)
    implementation("com.google.auth", "google-auth-library-oauth2-http", googleAuthLibrary)
    implementation("com.google.apis", "google-api-services-people", googlePersonApi)

    implementation("io.github.dokar3", "sonner", toastVersion)
    implementation("com.mohamedrejeb.richeditor", "richeditor-compose", richEditorVersion)

    implementation("org.apache.poi", "poi-ooxml", apachePoiApi)

    implementation("org.slf4j", "slf4j-api", sl4jVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            includeAllModules = true
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            packageName = "SecVault"
            packageVersion = "1.0.0"

            description = "A secure vault application for managing encrypted data."
            vendor = "Shade Dev"
            copyright = "© 2025 Shade Dev. All rights reserved."

            windows {
                menuGroup = "SecVault"
                shortcut = true
                perUserInstall = false
                iconFile.set(project.file("src/main/resources/assets/icon.ico"))
            }

            macOS {
                dockName = "SecVault"
            }

            linux {
                shortcut = true
                iconFile.set(project.file("src/main/resources/assets/icon.png"))
            }
        }
    }
}
