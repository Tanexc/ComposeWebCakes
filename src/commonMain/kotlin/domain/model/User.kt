package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = -1,
    val login: String,
    val name: String,
    val surname: String,
    val chatIds: List<Long> = emptyList(),
    val creationTimestamp: Long = 0,
    val password: String = "",
    val token: String = ""
): Domain() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as User

        if (id != other.id) return false
        if (login != other.login) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (chatIds != other.chatIds) return false
        if (creationTimestamp != other.creationTimestamp) return false
        if (password != other.password) return false
        return token == other.token
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + chatIds.hashCode()
        result = 31 * result + creationTimestamp.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + token.hashCode()
        return result
    }
}
