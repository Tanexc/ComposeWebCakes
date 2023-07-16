package presentation.style.strings

import domain.interfaces.StringsPackage

sealed class Strings {

    val app_name: Int = 0
    val info: Int = 1
    val chat: Int = 2
    val feedback: Int = 3

    object EN: StringsPackage, Strings() {
        override val strings = mapOf(
            Pair(app_name, "WebComposeCake"),
            Pair(info, "About us"),
            Pair(chat, "Chat"),
            Pair(feedback, "Feedback")
        )
    }

    object RU: StringsPackage, Strings() {
        override val strings = mapOf(
            Pair(app_name, "WebComposeCake"),
            Pair(info, "О нас"),
            Pair(chat, "Чат"),
            Pair(feedback, "Отзывы")
        )
    }

    object VALUES: Strings()
}

val Locale = Strings.RU

