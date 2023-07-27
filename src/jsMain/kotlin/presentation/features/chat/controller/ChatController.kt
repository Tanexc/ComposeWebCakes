package presentation.features.chat.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import domain.model.Message
import domain.use_case.GetMessagesUseCase
import domain.use_case.GetClientIdUseCase
import domain.use_case.PostMessageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatController {
    val getMessagesUseCase = GetMessagesUseCase()
    val postMessageUseCase = PostMessageUseCase()

    val getClientIdUseCase = GetClientIdUseCase()


    private val _messageList: MutableState<List<Message>> = mutableStateOf(emptyList())
    val messageList by _messageList

    init {
        getMessagesUseCase().onEach {
            _messageList.value = it
        }.launchIn(CoroutineScope(Dispatchers.Default))
    }



}