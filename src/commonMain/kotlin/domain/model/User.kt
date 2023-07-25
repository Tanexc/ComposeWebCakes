package domain.model

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val chatIds: List<Long>,
    val creationTimestamp: Long,
    val password: ByteArray,
    val token: String
): Domain {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (chatIds != other.chatIds) return false
        if (creationTimestamp != other.creationTimestamp) return false
        return password.contentEquals(other.password)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + chatIds.hashCode()
        result = 31 * result + creationTimestamp.hashCode()
        result = 31 * result + password.contentHashCode()
        return result
    }
}
