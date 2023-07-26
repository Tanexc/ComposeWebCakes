package ru.tanexc.application.data.database.dao

import domain.model.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import ru.tanexc.application.data.database.entity.ChatTable
import ru.tanexc.application.data.database.entity.UserTable
import ru.tanexc.application.data.database.factory.DatabaseFactory.dbQuery
import ru.tanexc.application.domain.interfaces.UserDao

class UserDaoImpl : UserDao {
    override suspend fun insert(user: User): User? = UserTable
        .asDomain { table ->
            dbQuery {
                (table as UserTable)
                    .insert { row ->
                        row[name] = user.name
                        row[surname] = user.surname
                        row[chatIds] = user.chatIds.joinToString(" ")
                        row[creationTimestamp] = user.creationTimestamp
                        row[password] = user.password
                        row[token] = user.token
                    }
                    .resultedValues?.firstOrNull()
            }
        }

    override suspend fun getById(id: Long): User? = UserTable
        .asDomain { table ->
            dbQuery {
                (table as UserTable)
                    .select {
                        ChatTable.id eq id
                    }
                    .firstOrNull()
            }
        }

    override suspend fun edit(user: User): Unit =
        dbQuery {
            UserTable
                .update { row ->
                    row[name] = user.name
                    row[surname] = user.surname
                    row[chatIds] = user.chatIds.joinToString(" ")
                    row[creationTimestamp] = user.creationTimestamp
                    row[password] = user.password
                    row[token] = user.token
                }
        }
}