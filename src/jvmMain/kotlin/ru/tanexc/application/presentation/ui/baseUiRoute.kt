package ru.tanexc.application.presentation.ui

import constants.Application
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.baseUiRoute() {
    get(Application.BASE_UI_URL) {
        call.respondText(
            this::class.java.classLoader.getResource(Application.BASE_UI_HTML_FILE)!!.readText(),
            ContentType.Text.Html
        )
    }
}