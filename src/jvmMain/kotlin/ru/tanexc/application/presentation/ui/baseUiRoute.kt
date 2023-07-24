package ru.tanexc.application.presentation.ui

import constants.Deployment
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.baseUiRoute() {
    get(Deployment.BASE_UI_URL) {
        call.respondText(
            this::class.java.classLoader.getResource(Deployment.BASE_UI_HTML_FILE)!!.readText(),
            ContentType.Text.Html
        )
    }
}