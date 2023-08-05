package ru.tanexc.application.core.util.config

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.core.component.KoinComponent

class ConfigTool: KoinComponent {

    private val config = HoconApplicationConfig(ConfigFactory.load())

    fun getProperty(key: String): String? = config.propertyOrNull(key)?.getString()
}