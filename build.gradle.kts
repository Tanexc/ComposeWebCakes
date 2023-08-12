val kotlinVersion: String by project
val composeVersion: String by project
val logbackVersion: String by project
val ktorVersion: String by project
val skikoVersion: String by project
val coroutinesVersion: String by project
val skikoRuntimeVersion: String by project
val koinVersion: String by project
val exposedVersion: String by project
val postgresqlVersion: String by project

plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.0-beta01"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    application
}

group = "ru.tanexc"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    google()

}



kotlin {

    jvm {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
                jvmTarget = "1.8"
            }
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                // Compose
                implementation(compose.runtime)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.html.core)
                implementation(compose.foundation)
                implementation(compose.material3)

                // Serialization
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                // Koin
                implementation("io.insert-koin:koin-core:$koinVersion")

                // Bcrypt
                implementation("at.favre.lib:bcrypt:0.10.2")
            }
        }

        val jsMain by getting {
            dependencies {

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-websockets:$ktorVersion")

                // Skiko
                implementation("org.jetbrains.skiko:skiko-js:$skikoVersion")
                implementation("org.jetbrains.skiko:skiko-js-runtime:$skikoRuntimeVersion")

                // Serialization
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                //implementation("org.jetbrains.compose.material:material-icons-core-js:1.4.3")
                //implementation("org.jetbrains.compose.material:material-icons-extended-js:1.4.3")

                dependsOn(commonMain)
            }

        }

        val jvmMain by getting {
            dependencies {

                // Ktor
                implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-websockets-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-sessions-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")

                // Serialization
                implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")


                // Exposed
                implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

                // PostgreSQL
                implementation("org.postgresql:postgresql:$postgresqlVersion")

                // Html
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.1")

                // Logs
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // Koin
                implementation("io.insert-koin:koin-ktor:$koinVersion")

                // SLF4J Logger
                implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

                dependsOn(commonMain)
            }
        }

    }
}

application {
    mainClass.set("ru.tanexc.application.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
    classpath("androidx.compose.runtime:runtime:1.5.0-beta01")
}

compose.experimental {
    web.application {}
}

compose {
    kotlinCompilerPlugin.set(composeVersion)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=$kotlinVersion")
}