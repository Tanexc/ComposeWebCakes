package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RespondData<T>(
    val data: T? = null,
    val message: String? = null
)