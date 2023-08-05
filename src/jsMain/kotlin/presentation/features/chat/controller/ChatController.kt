package presentation.features.chat.controller

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import domain.controller.SettingsController
import domain.model.Message
import domain.use_case.client.GetClientIdUseCase
import domain.use_case.client.GetClientNameUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatController: KoinComponent {
    val getClientNameUseCase: GetClientNameUseCase by inject()
    val getClientIdUseCase: GetClientIdUseCase by inject()
    val settings: SettingsController by inject()

    private val _messageList: MutableState<List<Message>> = mutableStateOf(emptyList())
    val messageList by _messageList

    private val _textFieldValue: MutableState<String> = mutableStateOf("")
    val textFieldValue: String by _textFieldValue

    private val _showInsertNameDialog: MutableState<Boolean> = mutableStateOf(false)
    val showInsertNameDialog: Boolean by _showInsertNameDialog

    private val _clientName: MutableState<String?> = mutableStateOf(getClientNameUseCase())
    val clientName: String? by _clientName

    private val _clientId: MutableState<String?> = mutableStateOf(null)
    val clientId: String? by _clientId

    private val _chatId: MutableState<Long?> = mutableStateOf(null)
    val chatId: Long? by _chatId

    val lazyColumnState = rememberLazyListState()


    init {
        if (clientName == null) _showInsertNameDialog.value = true

    }

    fun setClientName(name: String) {
        setClientName(name)
    }

    fun updateChatId(id: Long) {
        _chatId.value = id
    }

    fun checkIfNameExist(): Boolean = clientName == null


    fun updateTextFieldValue(value: String) {
        _textFieldValue.value = value
    }
}