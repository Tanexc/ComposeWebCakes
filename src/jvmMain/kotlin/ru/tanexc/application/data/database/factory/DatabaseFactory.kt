package ru.tanexc.application.data.database.factory

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcURL = "jdbc:sqlite:file:./data/feed/db.db"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(Recipes)
            SchemaUtils.create(Posts)
            SchemaUtils.create(Categories)
            SchemaUtils.create(Ingredients)
            SchemaUtils.create(Comments)
            SchemaUtils.create(Topics)
            SchemaUtils.create(FileDatas)
            SchemaUtils.create(Messages)
            SchemaUtils.create(Chats)
            SchemaUtils.create(Replies)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}