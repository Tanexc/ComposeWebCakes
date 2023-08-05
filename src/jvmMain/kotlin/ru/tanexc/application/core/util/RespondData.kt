package ru.tanexc.application.core.util

import kotlinx.serialization.*

@Serializable
data class RespondData<T>(
    val data: T? = null,
    val message: String? = null
)