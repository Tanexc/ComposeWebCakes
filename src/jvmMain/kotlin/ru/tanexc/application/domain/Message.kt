package ru.tanexc.application.domain

data class Message(
    val id: Long,
    val replyTo: Message?,
    val timestamp: Long,

    val text: String,
    val sender: Long
) {
}