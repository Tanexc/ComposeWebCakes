package core.util

import io.ktor.util.*
import io.ktor.utils.io.core.*
import org.jetbrains.skiko.currentNanoTime
import kotlin.random.Random


object HashTool {

    private val digest = Digest("SHA-256")

    @OptIn(InternalAPI::class)
    suspend fun getHashByteArray(value: String): ByteArray {
        return digest.build(
            value
        )
    }

    private suspend fun getHashPart(values: List<String>, length: Int = Int.MAX_VALUE): String {
        val hash = getHashByteArray(values.toString()).joinToString("") { it.toString(16).padStart(2, '0') }
        return if (length > hash.length) {
            hash
        } else {
            hash.substring(hash.length - length, hash.length)
        }
    }

    suspend fun generateUserId(): String {
        return getHashPart(
            listOf(
                currentNanoTime().toString(),
                Random.nextLong(1000000).toString(),
                Random.nextBytes(100).toString()
                )
        )
    }
}