package presentation.features.chat.controller

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import data.websocket.chat.ChatWebsocketServiceImpl
import domain.interfaces.WebsocketService
import domain.model.Chat
import domain.model.Message
import domain.repository.ClientRepository
import domain.use_case.chat.ChatCreateUseCase
import domain.use_case.chat.ChatGetByClientIdUseCase
import domain.use_case.message.MessageGetByClientIdUseCase
import domain.use_case.message.MessageGetByIdUseCase
import io.ktor.util.date.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class ClientChatController : KoinComponent {
    private val clientRepository: ClientRepository by inject()

    val chatWebsocketService: ChatWebsocketServiceImpl by inject()

    private val getChatByIdUseCase: ChatGetByClientIdUseCase by inject()
    private val createChatUseCase: ChatCreateUseCase by inject()

    private val getMessageByIdUseCase: MessageGetByIdUseCase by inject()
    private val getMessageByClientId: MessageGetByClientIdUseCase by inject()

    private val _messageList: MutableState<List<Message>> = mutableStateOf(emptyList())
    val messageList by _messageList

    private val _showInsertNameDialog: MutableState<Boolean> = mutableStateOf(false)
    val showInsertNameDialog: Boolean by _showInsertNameDialog

    private val _showErrorDialog: MutableState<Boolean> = mutableStateOf(false)
    val showErrorDialog: Boolean by _showErrorDialog

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: Boolean by _loading

    private val _clientName: MutableState<String?> = mutableStateOf(clientRepository.getClientName())
    val clientName: String? by _clientName

    private val _clientId: MutableState<String?> = mutableStateOf(null)
    val clientId: String? by _clientId

    private val _chat: MutableState<Chat?> = mutableStateOf(null)
    val chat: Chat? by _chat

    init {
        _clientName.value = clientRepository.getClientName()
        initializeChat()
    }

    private fun initializeChat() {
        CoroutineScope(Dispatchers.Default).launch {
            clientRepository.getClientId().collect {
                _clientId.value = it
            }

            if (clientName == null) _showInsertNameDialog.value = true
            else getChat(clientId!!, clientName!!)

        }.invokeOnCompletion {
            if (clientName != null) {
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

    fun setClientName(value: String) {
        clientRepository.setClientName(value)
        _clientName.value = value
        initializeChat()
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
                    _showErrorDialog.value = true
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
                        _showErrorDialog.value = true
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
                    _showErrorDialog.value = true
                }
            }
        }
    }

    suspend fun getMessageById(id: Long): Message? {
        return getMessageByIdUseCase(id).data
    }

    fun sendMessage(text: String) {
        CoroutineScope(Dispatchers.Default).launch {
            clientId?.let { sender -> chatWebsocketService.send(
                Message(
                    id = -1L,
                    replyTo = null,
                    sender = sender,
                    text = text,
                    timestamp = getTimeMillis()
                )
            )
            }
        }

    }

}