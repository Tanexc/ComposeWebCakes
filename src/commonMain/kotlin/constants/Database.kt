package constants

object Database {
    const val jdbcURL = config.property("storage.jdbcURL").getString()
    const val USER = ""
    const val PASSWORD = ""
    const val DRIVER = config.property("storage.driverClassName").getString()
}