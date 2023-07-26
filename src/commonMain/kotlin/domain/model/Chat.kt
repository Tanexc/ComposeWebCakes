package domain.model

data class Chat(
    val id: Long,
    val userId: String,
    val title: String?,
    val messages: List<Long>,
    val creationTimestamp: Long,
    val newMessagesCount: Int
): Domain
