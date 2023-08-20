package presentation.features.chat.controller

import androidx.compose.runtime.*
import core.util.Dialogs
import data.websocket.chat.ChatWebsocketServiceImpl
import domain.model.Chat
import domain.model.Message
import domain.repository.ClientRepository
import domain.use_case.chat.ChatCreateUseCase
import domain.use_case.chat.ChatGetByClientIdUseCase
import domain.use_case.message.MessageGetByClientIdUseCase
import domain.use_case.message.MessageGetByIdUseCase
import io.ktor.util.date.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class ClientChatController : KoinComponent {
    private val clientRepository: ClientRepository by inject()

    val chatWebsocketService: ChatWebsocketServiceImpl by inject()
    private var websocketConnectionEnabled: Boolean = false

    private val getChatByIdUseCase: ChatGetByClientIdUseCase by inject()
    private val createChatUseCase: ChatCreateUseCase by inject()

    private val getMessageByIdUseCase: MessageGetByIdUseCase by inject()
    private val getMessageByClientId: MessageGetByClientIdUseCase by inject()

    private val _messageList: MutableState<List<Message>> = mutableStateOf(emptyList())
    val messageList by _messageList

    private val _showInsertNameDialog: MutableState<Boolean> = mutableStateOf(false)
    val showInsertNameDialog: Boolean by _showInsertNameDialog

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: Boolean by _loading

    private val _clientName: MutableState<String?> = mutableStateOf(clientRepository.getClientName())
    val clientName: String? by _clientName

    private val _clientId: MutableState<String?> = mutableStateOf(null)
    val clientId: String? by _clientId

    private val _chat: MutableState<Chat?> = mutableStateOf(null)
    val chat: Chat? by _chat

    private val _dialogToShow: MutableState<Dialogs> = mutableStateOf(Dialogs.None)
    val dialogToShow: Dialogs by _dialogToShow

    private val _dialogMessage: MutableState<String> = mutableStateOf("")
    val dialogMessage: String by _dialogMessage

    init {
        updateName()
        initializeChat()
    }

    fun initializeChat() {
        if (chat == null) {
            CoroutineScope(Dispatchers.Default).launch {
                clientRepository.getClientId().collect {
                    _clientId.value = it
                }

                if (clientName == null) _showInsertNameDialog.value = true
                else getChat(clientId!!, clientName!!)

            }.invokeOnCompletion {
                if (clientName != null && !websocketConnectionEnabled) {
                    websocketConnectionEnabled = true
                    CoroutineScope(Dispatchers.Default).launch {
                        getChatMessages(clientId!!)
                        chatWebsocketService(
                            clientId = clientId!!,
                            clientName = clientName!!
                        )?.collect {
                            _messageList.value = listOf(it) + _messageList.value
                        }
                    }
                }
            }
        }
    }

    private suspend fun getChat(clientId: String, clientName: String) {
        getChatByIdUseCase(clientId).collect {
            when (it) {
                is State.Processing -> _loading.value = true
                is State.Success -> {
                    _chat.value = it.data
                    _loading.value = false
                }

                else -> {
                    _loading.value = false
                    _dialogToShow.value = Dialogs.Error
                }
            }
        }

        if (chat == null) {
            createChatUseCase(clientId, clientName).collect {
                when (it) {
                    is State.Processing -> _loading.value = true
                    is State.Success -> {
                        _chat.value = it.data
                        _loading.value = false
                    }

                    else -> {
                        _loading.value = false
                        _dialogToShow.value = Dialogs.Error
                    }
                }
            }
        }
    }

    private suspend fun getChatMessages(clientId: String) {
        getMessageByClientId(clientId).collect {
            when (it) {
                is State.Processing -> _loading.value = true
                is State.Success -> {
                    _messageList.value = it.data!!.reversed()
                    _loading.value = false
                }

                else -> {
                    _loading.value = false
                    _dialogToShow.value = Dialogs.Error
                }
            }
        }
    }

    fun sendMessage(text: String, replyTo: Long? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            clientName?.let { sender -> chatWebsocketService.send(
                Message(
                    id = -1L,
                    replyTo = replyTo,
                    sender = sender,
                    text = text,
                    timestamp = getTimeMillis()
                )
            )
            }
        }

    }

    fun updateName() {
        if (clientName == null) {
            _clientName.value = clientRepository.getClientName()
        } else {
            _showInsertNameDialog.value = false
        }
    }

    fun closeDialog() {
        _dialogToShow.value = Dialogs.None
    }

    fun setDialog(value: Dialogs, message: String) {
        _dialogToShow.value = value
        _dialogMessage.value = message
    }

    suspend fun getMessageById(id: Long): Message? = getMessageByIdUseCase(id).first().data
}