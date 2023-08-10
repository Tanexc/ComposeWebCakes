package presentation.features.application.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import core.util.Screen
import domain.model.User
import domain.repository.ClientRepository
import domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.style.ui.theme._applicationColorScheme
import presentation.style.ui.theme._applicationUseDarkTheme
import presentation.style.ui.theme.getTheme
import util.State

class MainController: KoinComponent {
    private val clientRepository: ClientRepository by inject()
    private val userRepository: UserRepository by inject()

    private val _clientId: MutableState<String?> = mutableStateOf(null)
    val clientId: String? by _clientId

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.Info)
    val currentScreen: Screen by _currentScreen

    val items = listOf(
        Screen.Info,
        Screen.FeedBack,
        Screen.Chat,
        Screen.Settings
    )

    init {

        _applicationColorScheme.value = getTheme(clientRepository.getTheme().useDarkTheme)
        _applicationUseDarkTheme.value = clientRepository.getTheme().useDarkTheme

        CoroutineScope(Dispatchers.Default).launch {
            clientRepository.getClientId().collect {
                _clientId.value = it
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            val token = clientRepository.getToken()
            if (user == null && token != null) {
                userRepository.getByToken(token).collect {
                    when(it) {
                        is State.Success -> _user.value = it.data
                        else -> {}
                    }
                }
            }
        }
    }

    fun updateScreen(value: Screen) {
        _currentScreen.value = value
    }

    fun updateUser(value: User?) {
        _user.value = value
    }
}