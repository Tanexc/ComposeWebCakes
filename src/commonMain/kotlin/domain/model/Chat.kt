package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: Long,
    val clientId: String,
    val title: String?,
    val messages: List<Long>,
    val creationTimestamp: Long,
    val newMessagesCount: Int
): Domain()
