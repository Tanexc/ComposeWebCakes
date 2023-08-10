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
    const val name: Int = 9
    const val login: Int = 10
    const val surname: Int = 11
    const val password: Int = 12
    const val language: Int = 13
    const val repeatPassword: Int = 14
    const val registration: Int = 15
    const val enter: Int = 16
    const val requiredField: Int = 17
    const val wrongPassword: Int = 18
    const val simplePassword: Int = 19
    const val send: Int = 20
    const val forAdministration: Int = 21

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
            typeName to "Enter a name",
            name to "Name",
            login to "Login",
            surname to "Surname",
            password to "Password",
            language to "Language",
            repeatPassword to "Repeat password",
            registration to "Registration",
            enter to "Enter",
            requiredField to "Required field",
            wrongPassword to "Wrong password",
            simplePassword to "Simple password",
            send to "Send",
            forAdministration to "For administration"


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
            language to "Язык",
            client to "Пользователь",
            typeName to "Введите имя",
            name to "Имя",
            login to "Логин",
            surname to "Фамилия",
            password to "Пароль",
            repeatPassword to "Повторите пароль",
            registration to "Регистрация",
            enter to "Вход",
            requiredField to "Необходимое поле",
            wrongPassword to "Неверный пароль",
            simplePassword to "Простой пароль",
            send to "Отправить",
            forAdministration to "Для администрации"
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


