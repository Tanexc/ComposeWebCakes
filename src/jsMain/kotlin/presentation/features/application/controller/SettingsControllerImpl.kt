package presentation.features.application.controller

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import domain.controller.SettingsController
import domain.interfaces.StringResources
import domain.model.Theme
import domain.model.User
import domain.repository.ClientRepository
import io.ktor.client.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.style.strings.getResources
import presentation.style.ui.theme.getTheme

class SettingsControllerImpl: SettingsController {
    private val clientRepository: ClientRepository<HttpClient> by inject()

    private val _theme: MutableState<ColorScheme> = mutableStateOf(getTheme(true))
    override val theme by _theme

    private val _locale: MutableState<StringResources> = mutableStateOf(getResources(clientRepository.getLocale()))
    override val locale: StringResources by _locale

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    override fun updateTheme(theme: Theme) {
        clientRepository.setTheme(theme)
    }


    override fun updateLocale(locale: StringResources) {
        clientRepository.setLocale(locale())
    }

    fun authorizeUser(
        login: String,
        password: String
    ) {

    }

}