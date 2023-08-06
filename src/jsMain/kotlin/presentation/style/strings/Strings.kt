package presentation.style.strings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import domain.interfaces.StringResources

object Strings {

    const val appName: Int = 0
    const val aboutUs: Int = 1
    const val chat: Int = 2
    const val feedback: Int = 3
    const val settings: Int = 4
    const val typeMessage: Int = 5
    const val useDarkTheme: Int = 6
    const val client: Int = 7
    const val typeName: Int = 8

    object EN: StringResources {
        override val strings = mapOf(
            appName to "WebComposeCake",
            aboutUs to "About us",
            chat to "Chat",
            feedback to "Feedback",
            settings to "Settings",
            typeMessage to "Type message...",
            useDarkTheme to "Dark Theme",
            client to "Client",
            typeName to "Enter a name"
        )

        override fun invoke(): Int = 0

    }

    object RU: StringResources {
        override val strings = mapOf(
            appName to "WebComposeCake",
            aboutUs to "О нас",
            chat to "Чат",
            feedback to "Отзывы",
            settings to "Настройки",
            typeMessage to "Напишите сообщение...",
            useDarkTheme to "Тёмная тема",
            client to "Пользователь",
            typeName to "Введите имя"
        )

        override fun invoke(): Int = 1
    }
}

fun getResources(locale: Int): StringResources {
    return when(locale) {
        Strings.RU() -> Strings.RU
        Strings.EN() -> Strings.EN
        else -> {
            Strings.EN
        }
    }
}

val _applicationResources: MutableState<StringResources> = mutableStateOf(Strings.RU)
val applicationResources: StringResources by _applicationResources


