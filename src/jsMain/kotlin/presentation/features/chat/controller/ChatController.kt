package presentation.features.chat.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import domain.model.Message

class ChatController {

    private val _messageList: MutableState<List<Message>> = mutableStateOf(emptyList())
    val messageList by _messageList




}