package presentation.style.strings

import domain.interfaces.StringResources

sealed class Strings {

    val appName: Int = 0
    val aboutUs: Int = 1
    val chat: Int = 2
    val feedback: Int = 3

    object EN: StringResources, Strings() {
        override val strings = mapOf(
            Pair(appName, "WebComposeCake"),
            Pair(aboutUs, "About us"),
            Pair(chat, "Chat"),
            Pair(feedback, "Feedback")
        )

        override fun invoke(): Int = 0

    }

    object RU: StringResources, Strings() {
        override val strings = mapOf(
            Pair(appName, "WebComposeCake"),
            Pair(aboutUs, "О нас"),
            Pair(chat, "Чат"),
            Pair(feedback, "Отзывы")
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


