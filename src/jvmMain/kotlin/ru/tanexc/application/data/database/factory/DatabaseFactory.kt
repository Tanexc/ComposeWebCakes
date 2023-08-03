package ru.tanexc.application.data.database.factory

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import ru.tanexc.application.core.util.config.ConfigTool
import ru.tanexc.application.data.database.entity.ChatTable
import ru.tanexc.application.data.database.entity.MessageTable
import ru.tanexc.application.data.database.entity.UserTable

object DatabaseFactory: KoinComponent {

    private val config = ConfigTool()

    fun init() {

        val driverClassName = config.getProperty("storage.driverClassName").toString()
        val jdbcURL = config.getProperty("storage.jdbcURL").toString()

        println(jdbcURL)

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