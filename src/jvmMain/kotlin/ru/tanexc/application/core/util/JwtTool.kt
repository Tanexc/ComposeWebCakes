package ru.tanexc.application.core.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.util.date.*
import java.util.*

object JwtTool {

    private val config = HoconApplicationConfig(ConfigFactory.load())

    fun generateToken(
        name: String,
        surname: String,
        creationTimestamp: Long,
        login: String,
        oldToken: String
    ): String {
        return JWT.create()
            .withIssuer(config.property("jwt.issuer").toString())
            .withSubject(config.property("jwt.subject").toString())
            .withClaim("arg1", name)
            .withClaim("arg2", surname)
            .withClaim("arg3", creationTimestamp)
            .withClaim("arg4", oldToken.toByteArray().shuffle().toString())
            .withClaim("arg5", login)
            .withExpiresAt(Date(getTimeMillis() + 72 * 3_600_000))
            .sign(Algorithm.HMAC256(config.property("jwt.secret").toString()))
    }

    fun String.isNotExpired() = JWT.decode(this).expiresAt.time > getTimeMillis()
}