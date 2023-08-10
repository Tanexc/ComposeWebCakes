package ru.tanexc.application.core.util

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.util.*

object PasswordHashTool {
    fun getHash(password: String) = BCrypt.withDefaults().hashToString(15, password.toCharArray())

    fun verifyHash(password: String, expected: String) = BCrypt.verifyer().verify(password.toCharArray(), expected.toCharArray()).verified

}
