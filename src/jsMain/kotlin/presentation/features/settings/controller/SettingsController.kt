package presentation.features.settings.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import domain.interfaces.StringResources
import domain.model.Theme
import domain.model.User
import domain.repository.ClientRepository
import domain.use_case.user.UserSignInUseCase
import domain.use_case.user.UserSignUpUseCase
import domain.use_case.user.UserUpdatePasswordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.style.strings._applicationResources
import presentation.style.ui.theme._applicationColorScheme
import presentation.style.ui.theme._applicationUseDarkTheme
import presentation.style.ui.theme.applicationUseDarkTheme
import presentation.style.ui.theme.getTheme
import util.State

class SettingsController(
    user: User?
) : KoinComponent {
    private val clientRepository: ClientRepository by inject()

    private val userSignInUseCase: UserSignInUseCase by inject()
    private val userSignUpUseCase: UserSignUpUseCase by inject()
    private val userUpdatePasswordUseCase: UserUpdatePasswordUseCase by inject()

    private val _user: MutableState<User?> = mutableStateOf(user)
    val user: User? by _user

    private val _clientName: MutableState<String?> = mutableStateOf(null)
    val clientName: String? by _clientName

    private val _stateProcessing: MutableState<Boolean> = mutableStateOf(false)
    val stateProcessing: Boolean by _stateProcessing

    private val _showErrorDialog: MutableState<Boolean> = mutableStateOf(false)
    val showErrorDialog: Boolean by _showErrorDialog

    private val _errorMessage: MutableState<String> = mutableStateOf("")
    val errorMessage: String by _errorMessage

    init {
        _clientName.value = clientRepository.getClientName()
    }

    fun updateTheme(value: Boolean) {
        _applicationColorScheme.value = getTheme(value)
        clientRepository.setTheme(Theme(0, value))
    }

    fun updateLocale(value: StringResources) {
        _applicationResources.value = value
        clientRepository.setLocale(value())
    }

    fun signInUser(login: String, password: String, onUserUpdate: (User) -> Unit) {

        CoroutineScope(Dispatchers.Main).launch {
            userSignInUseCase(login, password).collect {
                when (it) {
                    is State.Success -> {
                        _stateProcessing.value = false
                        _user.value = it.data
                        onUserUpdate(it.data!!)
                    }

                    is State.Processing -> {
                        _stateProcessing.value = true
                    }

                    is State.Error -> {
                        _errorMessage.value = it.message
                        _showErrorDialog.value = true
                    }

                    else -> {}
                }
            }
        }

    }

    fun signUpUser(login: String, password: String, name: String, surname: String, onUserUpdate: (User) -> Unit) {

        CoroutineScope(Dispatchers.Main).launch {
            userSignUpUseCase(
                User(
                    login = login,
                    name = name,
                    surname = surname
                ),
                password
            ).collect {
                when (it) {
                    is State.Success -> {
                        _stateProcessing.value = false
                        _user.value = it.data
                        onUserUpdate(it.data!!)
                    }

                    is State.Processing -> {
                        _stateProcessing.value = true
                    }

                    is State.Error -> {
                        _errorMessage.value = it.message
                        _showErrorDialog.value = true
                    }

                    else -> {}
                }
            }
        }
    }

    fun updatePassword(value: String, onUserUpdate: (User) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            userUpdatePasswordUseCase(
                user!!.token,
                value
            ).collect {
                when (it) {
                    is State.Success -> {
                        _stateProcessing.value = false
                        onUserUpdate(it.data!!)
                    }

                    is State.Processing -> {
                        _stateProcessing.value = true
                    }

                    is State.Error -> {
                        _errorMessage.value = it.message
                        _showErrorDialog.value = true
                    }

                    else -> {}
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
}