package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Long,
    val replyTo: Long?,
    val timestamp: Long,

    val text: String,
    val sender: String
): Domain