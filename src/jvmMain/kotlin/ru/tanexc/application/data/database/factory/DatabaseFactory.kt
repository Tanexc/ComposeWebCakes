package ru.tanexc.application.data.database.factory

import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.tanexc.application.data.database.entity.ChatTable
import ru.tanexc.application.data.database.entity.MessageTable
import ru.tanexc.application.data.database.entity.UserTable

object DatabaseFactory {
    fun init(config: ApplicationConfig) {

        val driverClassName = config.property("storage.driverClassName").toString()
        val jdbcURL = config.property("storage.jdbcURL").toString()

        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(ChatTable)
            SchemaUtils.create(MessageTable)
            SchemaUtils.create(UserTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}