package presentation.features.settings.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import core.util.Dialogs
import domain.interfaces.StringResources
import domain.model.Theme
import domain.model.User
import domain.repository.ClientRepository
import domain.use_case.user.UserGetByTokenUseCase
import domain.use_case.user.UserSignInUseCase
import domain.use_case.user.UserSignUpUseCase
import domain.use_case.user.UserUpdatePasswordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.style.strings.Strings.welcome
import presentation.style.strings._applicationResources
import presentation.style.strings.applicationResources
import presentation.style.ui.theme._applicationColorScheme
import presentation.style.ui.theme._applicationUseDarkTheme
import presentation.style.ui.theme.applicationUseDarkTheme
import presentation.style.ui.theme.getTheme
import util.State

class SettingsController : KoinComponent {
    private val clientRepository: ClientRepository by inject()

    private val userSignInUseCase: UserSignInUseCase by inject()
    private val userSignUpUseCase: UserSignUpUseCase by inject()
    private val userUpdatePasswordUseCase: UserUpdatePasswordUseCase by inject()
    private val getByTokenUseCase: UserGetByTokenUseCase by inject()

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _clientName: MutableState<String?> = mutableStateOf(null)
    val clientName: String? by _clientName

    private val _stateProcessing: MutableState<Boolean> = mutableStateOf(false)
    val stateProcessing: Boolean by _stateProcessing

    private val _dialogToShow: MutableState<Dialogs> = mutableStateOf(Dialogs.Success)
    val dialogToShow: Dialogs by _dialogToShow

    private val _dialogMessage: MutableState<String> = mutableStateOf("")
    val dialogMessage: String by _dialogMessage

    init {
        _clientName.value = clientRepository.getClientName()
        CoroutineScope(Dispatchers.Main).launch {
            getByTokenUseCase(clientRepository.getToken() ?: "").collect {
                when (it) {
                    is State.Success -> {
                        _user.value = it.data
                    }

                    else -> {}
                }
            }
        }
    }

    fun updateTheme(value: Boolean) {
        _applicationColorScheme.value = getTheme(value)
        clientRepository.setTheme(Theme(0, value))
    }

    fun updateLocale(value: StringResources) {
        _applicationResources.value = value
        clientRepository.setLocale(value())
    }

    fun signInUser(login: String, password: String, onUserUpdate: (User?) -> Unit) {

        CoroutineScope(Dispatchers.Main).launch {
            userSignInUseCase(login, password).collect { state ->
                when (state) {
                    is State.Success -> {
                        _stateProcessing.value = false
                        _user.value = state.data
                        clientRepository.setToken(user!!.token)
                        onUserUpdate(state.data!!)
                        _dialogToShow.value = Dialogs.Success
                        _dialogMessage.value =
                            applicationResources(welcome) + (user?.let { ", ${it.name} ${it.surname}." } ?: "!")
                    }

                    is State.Processing -> {
                        _stateProcessing.value = true
                    }

                    is State.Error -> {
                        _dialogToShow.value = Dialogs.Error
                        _dialogMessage.value = state.message
                        _stateProcessing.value = false
                    }

                    else -> {_stateProcessing.value = false}
                }
            }
        }

    }

    fun signUpUser(login: String, password: String, name: String, surname: String, onUserUpdate: (User?) -> Unit) {

        CoroutineScope(Dispatchers.Main).launch {
            userSignUpUseCase(
                User(
                    login = login,
                    name = name,
                    surname = surname
                ),
                password
            ).collect { state ->
                when (state) {
                    is State.Success -> {
                        _stateProcessing.value = false
                        _user.value = state.data
                        clientRepository.setToken(user!!.token)
                        onUserUpdate(state.data)
                        _dialogToShow.value = Dialogs.Success
                        _dialogMessage.value =
                            applicationResources(welcome) + (user?.let { ", ${it.name} ${it.surname}." } ?: "!")
                    }

                    is State.Processing -> {
                        _stateProcessing.value = true
                    }

                    is State.Error -> {
                        _stateProcessing.value = false
                        _dialogToShow.value = Dialogs.Error
                        _dialogMessage.value = state.message
                    }

                    else -> {_stateProcessing.value = false}
                }
            }
        }
    }

    fun updatePassword(value: String, onUserUpdate: (User?) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            userUpdatePasswordUseCase(
                user!!.token,
                value
            ).collect { state ->
                when (state) {
                    is State.Success -> {
                        _stateProcessing.value = false
                        _user.value = state.data
                        clientRepository.setToken(user!!.token)
                        onUserUpdate(state.data)
                        _dialogToShow.value = Dialogs.Success
                        _dialogMessage.value =
                            applicationResources(welcome) + (user?.let { ", ${it.name} ${it.surname}." } ?: "!")
                    }

                    is State.Processing -> {
                        _stateProcessing.value = true
                    }

                    is State.Error -> {
                        _stateProcessing.value = false
                        _dialogToShow.value = Dialogs.Error
                        _dialogMessage.value = state.message
                    }

                    else -> {_stateProcessing.value = false}
                }
            }
        }
    }

    fun updateUseDarkTheme() {
        _applicationUseDarkTheme.value = !applicationUseDarkTheme
        _applicationColorScheme.value = getTheme(applicationUseDarkTheme)
        clientRepository.setTheme(Theme(0, applicationUseDarkTheme))
    }

    fun updateClientName(value: String) {
        _clientName.value = value
        clientRepository.setClientName(value)
    }

    fun getClientName(): String? = clientRepository.getClientName()

    fun exitUser(onUserUpdate: (User?) -> Unit) {
        clientRepository.setToken("")
        _user.value = null
        onUserUpdate(null)
    }

    fun closeDialog() {
        _dialogToShow.value = Dialogs.None
    }

    fun setUser(value: User?) {
        if (user == null) {
            _user.value = value
        }
    }
}