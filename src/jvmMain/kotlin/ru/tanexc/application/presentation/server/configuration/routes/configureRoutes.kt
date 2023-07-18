package ru.tanexc.application.presentation.server.configuration.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.tanexc.application.presentation.api.chatApi.chatApiRoutes

fun Application.configureRoutes() = routing {

    // UI
    get("/") {
        call.respondText(
            this::class.java.classLoader.getResource("index.html")!!.readText(),
            ContentType.Text.Html
        )
    }

    // API
    chatApiRoutes()





    staticResources(
        remotePath = "/static",
        basePackage = null
    )
}
