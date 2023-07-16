package ru.tanexc.application

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.*
import io.ktor.server.netty.Netty
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "127.0.0.1"
    ) {
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            staticResources(
                remotePath = "/static",
                basePackage = null
            )
        }
    }.start(wait = true)
}