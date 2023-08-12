package presentation.features.chat.controller


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import core.util.Dialogs
import data.websocket.chat.ChatWebsocketServiceImpl
import domain.model.Chat
import domain.model.Message
import domain.model.User
import domain.repository.ClientRepository
import domain.use_case.chat.ChatCreateUseCase
import domain.use_case.chat.ChatGetAllUseCase
import domain.use_case.chat.ChatGetByClientIdUseCase
import domain.use_case.message.MessageGetByClientIdUseCase
import domain.use_case.message.MessageGetByIdUseCase
import io.ktor.util.date.*
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.State

class ChatController : KoinComponent {
    private val clientRepository: ClientRepository by inject()
    val chatWebsocketService: ChatWebsocketServiceImpl by inject()

    private val getMessageByIdUseCase: MessageGetByIdUseCase by inject()
    private val getMessageByClientId: MessageGetByClientIdUseCase by inject()
    private val getAllChatUseCase: ChatGetAllUseCase by inject()

    private val getChatByIdUseCase: ChatGetByClientIdUseCase by inject()
    private val createChatUseCase: ChatCreateUseCase by inject()

    private val _messageList: MutableState<List<Message>> = mutableStateOf(emptyList())
    val messageList by _messageList

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: Boolean by _loading

    private val _chatList: MutableState<List<Chat>?> = mutableStateOf(null)
    val chatList by _chatList

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _chat: MutableState<Chat?> = mutableStateOf(null)
    val chat: Chat? by _chat

    private var webSocketConnection: Job? = null

    private val _dialogToShow: MutableState<Dialogs> = mutableStateOf(Dialogs.None)
    val dialogToShow: Dialogs by _dialogToShow

    private val _dialogMessage: MutableState<String> = mutableStateOf("")
    val dialogMessage: String by _dialogMessage

    fun connectToChat(value: Chat) {

        _chat.value = value

        webSocketConnection = CoroutineScope(Dispatchers.Default).launch {
            chatWebsocketService.close()
            getChatMessages(chat!!.clientId)
            chatWebsocketService(
                clientId = chat!!.clientId,
                clientName = "t",
                userId = user!!.id
            )?.collect {
                _messageList.value = listOf(it) + _messageList.value
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
                }
            }
        }
    }

    fun initialize(value: User) {
        if (user == null) {
            _user.value = value
        }
    }

    fun sendMessage(text: String, replyTo: Long? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            user?.let { sender ->
                chatWebsocketService.send(
                    Message(
                        id = -1L,
                        replyTo = replyTo,
                        sender = sender.name,
                        text = text,
                        timestamp = getTimeMillis()
                    )
                )
            }
        }
    }

    fun getAllChats() {
        CoroutineScope(Dispatchers.Default).launch {
            getAllChatUseCase(user!!.token).collect { state ->
                when (state) {
                    is State.Processing -> {
                    }

                    is State.Error -> {
                        _dialogMessage.value = state.message
                        _dialogToShow.value = Dialogs.Error
                    }

                    is State.Success -> {
                        _chatList.value = state.data!!
                    }

                    else -> {}
                }
            }
        }
    }

    fun closeDialog() {
        _dialogToShow.value = Dialogs.None
    }
}