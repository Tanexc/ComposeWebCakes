val kotlinVersion: String by project
val composeVersion: String by project
val logbackVersion: String by project
val ktorVersion: String by project
val skikoVersion: String by project
val coroutinesVersion: String by project
val skikoRuntimeVersion: String by project
val koinVersion: String by project

plugins {
    kotlin("multiplatform") version "1.8.0"
    id("org.jetbrains.compose") version "1.4.0"
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

                // Koin
                implementation("io.insert-koin:koin-core:$koinVersion")

                // Serialization
                runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                // Bcrypt
                implementation("de.nycode:bcrypt:2.3.0")
            }
        }

        val jsMain by getting {
            dependencies {

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")

                // Skiko
                implementation("org.jetbrains.skiko:skiko-js:$skikoVersion")
                implementation("org.jetbrains.skiko:skiko-js-runtime:$skikoRuntimeVersion")

                dependsOn(commonMain)
            }

        }

        val jvmMain by getting {
            dependencies {

                // Ktor
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.1")

                // Logs
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

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
    classpath("androidx.compose.runtime:runtime:1.4.0")
}

compose.experimental {
    web.application {}
}

compose {
    kotlinCompilerPlugin.set(composeVersion)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=$kotlinVersion")
}
