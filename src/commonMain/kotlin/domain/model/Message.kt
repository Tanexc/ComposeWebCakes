package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Long,
    val replyTo: Message?,
    val timestamp: Long,

    val text: String,
    val sender: Long
): Domain {
    override fun asEntity(): DatabaseEntity {
        TODO("Not yet implemented")
    }
}