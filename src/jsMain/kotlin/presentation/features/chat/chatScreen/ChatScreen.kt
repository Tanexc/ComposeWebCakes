package presentation.features.chat.chatScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.util.Dialogs
import domain.model.Message
import domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext
import presentation.features.chat.components.MessageBubble
import presentation.features.chat.controller.ChatController
import presentation.features.util.shapes.Shape
import presentation.features.util.widgets.dialogs.BaseDialog
import presentation.features.util.widgets.text_fields.MessageInputTextField
import presentation.style.icons.filled.IconFilledSend
import presentation.style.icons.rounded.IconRoundedCake
import presentation.style.strings.Strings
import presentation.style.strings.Strings.typeMessage
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ChatScreen(
    user: User
) {
    val controller: ChatController by GlobalContext.get().inject()
    controller.initialize(user)

    var messageTextValue by remember { mutableStateOf("") }
    val replyMessage: MutableState<Message?> = remember { mutableStateOf(null) }

    val lazyColumnState = rememberLazyListState()
    val chatListLazyRowState = rememberLazyListState()

    LaunchedEffect(controller.messageList.size) {
        if (controller.messageList.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.widthIn(max = 800.dp).align(TopCenter)) {
            if (controller.chatList != null) {
                if (controller.chat == null) {
                    Box {
                        LazyRow(
                            modifier = Modifier.wrapContentHeight().widthIn(max = 804.dp),
                            state = chatListLazyRowState
                        ) {
                            items(controller.chatList!!) {
                                val colorHex = it.title!!.split("#")[1].substring(0, 6)
                                Column(modifier = Modifier
                                    .size(88.dp)
                                    .padding(4.dp)
                                    .border(
                                        1.dp,
                                        applicationColorScheme.outline,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = LocalIndication.current
                                    ) {
                                        controller.connectToChat(it)
                                    }
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .background(
                                                Color(
                                                    red = colorHex.substring(0, 2).hexToInt(),
                                                    green = colorHex.substring(2, 4).hexToInt(),
                                                    blue = colorHex.substring(4, 6).hexToInt()
                                                ),
                                                RoundedCornerShape(16.dp)
                                            )
                                            .fillMaxWidth()
                                            .height(60.dp)
                                            .align(CenterHorizontally)
                                    )
                                    Text(
                                        it.title,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = false,
                                        modifier = Modifier.align(CenterHorizontally)
                                            .padding(vertical = 4.dp, horizontal = 8.dp),
                                        style = Typography.labelSmall
                                    )
                                }

                            }
                        }
                        if (chatListLazyRowState.canScrollBackward) {
                            Spacer(
                                modifier = Modifier.size(64.dp, 88.dp).background(
                                    brush = Brush.horizontalGradient(
                                        listOf(applicationColorScheme.surface, Color.Transparent)
                                    )
                                )
                                    .align(CenterStart)
                            )
                        }
                        if (chatListLazyRowState.canScrollForward) {

                            Spacer(
                                modifier = Modifier.size(64.dp, 88.dp).background(
                                    brush = Brush.horizontalGradient(
                                        listOf(Color.Transparent, applicationColorScheme.surface)
                                    )
                                )
                                    .align(CenterEnd)
                            )
                        }
                    }
                    Row(Modifier.widthIn(max = 800.dp)) {
                        HorizontalScrollbar(
                            modifier = Modifier
                                .fillMaxWidth(),
                            adapter = rememberScrollbarAdapter(
                                scrollState = chatListLazyRowState
                            )
                        )
                    }
                }
            } else {
                controller.getAllChats()
            }

            if (controller.chat != null) {

                val chatColorHex = controller.chat!!.title!!.split("#")[1].substring(0, 6)

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.align(Center).padding(4.dp)) {

                        Row(modifier = Modifier.align(CenterHorizontally)) {
                            Box(
                                modifier = Modifier.background(
                                    color = Color(
                                        red = chatColorHex.substring(0, 2).hexToInt(),
                                        green = chatColorHex.substring(2, 4).hexToInt(),
                                        blue = chatColorHex.substring(4, 6).hexToInt()
                                    ),
                                    CircleShape
                                )
                                    .size(56.dp)
                            )

                            Text(
                                controller.chat?.title ?: "Untitled",
                                modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically)
                            )

                        }

                        Spacer(Modifier.size(8.dp))
                    }

                    IconButton(onClick = { controller.closeChat() }, modifier = Modifier.align(CenterEnd)) {
                        Icon(Icons.Filled.Close, null)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            applicationColorScheme.outline,
                            RoundedCornerShape(50)
                        )
                )

                    if (controller.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .size(48.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().weight(1f),
                            state = lazyColumnState,
                            reverseLayout = true
                        ) {
                            items(controller.messageList) { messageItem ->

                                val bubbleShape: RoundedCornerShape =
                                    when (controller.messageList.indexOf(messageItem)) {
                                        controller.messageList.lastIndex -> {
                                            if (controller.user!!.name == messageItem.sender)
                                                Shape.LastMessageShapeEnd()
                                            else
                                                Shape.LastMessageShapeStart()
                                        }

                                        0 -> {
                                            if (controller.user!!.name == messageItem.sender)
                                                Shape.FirstMessageShapeEnd()
                                            else
                                                Shape.FirstMessageShapeStart()
                                        }

                                        else -> {
                                            if (controller.user!!.name == messageItem.sender) {
                                                if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) + 1].sender != messageItem.sender
                                                ) {
                                                    Shape.LastMessageShapeEnd()
                                                } else if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) - 1].sender != messageItem.sender
                                                ) {
                                                    Shape.FirstMessageShapeEnd()
                                                } else {
                                                    Shape.MiddleMessageShapeEnd()
                                                }
                                            } else {
                                                if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) + 1].sender != messageItem.sender
                                                ) {
                                                    Shape.LastMessageShapeStart()
                                                } else if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) - 1].sender != messageItem.sender
                                                ) {
                                                    Shape.FirstMessageShapeStart()
                                                } else {
                                                    Shape.MiddleMessageShapeStart()
                                                }
                                            }
                                        }
                                    }

                                var replyTo: Message? by remember { mutableStateOf(null) }

                                CoroutineScope(Dispatchers.Default).launch {
                                    replyTo = controller.getMessageById(messageItem.replyTo ?: -1)
                                }

                                MessageBubble(
                                    message = messageItem,
                                    shape = bubbleShape,
                                    modifier = Modifier
                                        .background(
                                            applicationColorScheme.secondaryContainer.copy(0.6f),
                                            bubbleShape
                                        ),
                                    arrangement = if (controller.user!!.name == messageItem.sender) {
                                        Arrangement.End
                                    } else {
                                        Arrangement.Start
                                    },
                                    withName = (controller.user!!.name != messageItem.sender),
                                    nameColor = Color(
                                        red = chatColorHex.substring(0, 2).hexToInt(),
                                        green = chatColorHex.substring(2, 4).hexToInt(),
                                        blue = chatColorHex.substring(4, 6).hexToInt()
                                    ),
                                    replyTo = replyTo,
                                    onReply = { replyMessage.value = it }
                                )
                            }
                        }
                    }


                Spacer(Modifier.size(8.dp))

                MessageInputTextField(
                    backgroundColor = applicationColorScheme.secondaryContainer.copy(0.3f),
                    fontColor = contentColorFor(applicationColorScheme.secondaryContainer.copy(0.3f)),
                    onSend = {
                        controller.sendMessage(it, replyMessage.value?.id)
                        replyMessage.value = null
                    },
                    replyMessageColor = Color(
                        red = chatColorHex.substring(0, 2).hexToInt(),
                        green = chatColorHex.substring(2, 4).hexToInt(),
                        blue = chatColorHex.substring(4, 6).hexToInt()
                    ),
                    replyMessage = replyMessage.value,
                    onReplyClose = { replyMessage.value = null }
                )

                Spacer(Modifier.size(16.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier
                .align(CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = lazyColumnState
            ),
            reverseLayout = true
        )
    }

    if (controller.dialogToShow !is Dialogs.None) {
        BaseDialog(
            icon = controller.dialogToShow.icon,
            message = controller.dialogMessage,
            headlineText = controller.dialogToShow.headline
        ) {
            controller.closeDialog()
        }
    }


    /* Box(modifier = Modifier.fillMaxSize()) {
         Column(modifier = Modifier.align(TopCenter)) {
             *//*if (controller.chatList != null) {
                Box {
                    LazyRow(
                        modifier = Modifier.wrapContentHeight().widthIn(max = 804.dp),
                        state = chatListLazyRowState
                    ) {
                        items(controller.chatList!!) {
                            val colorHex = it.title!!.split("#")[1].substring(0, 6)
                            Column(modifier = Modifier
                                .size(88.dp)
                                .padding(4.dp)
                                .border(
                                    1.dp,
                                    applicationColorScheme.outline,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current
                                ) {
                                    controller.connectToChat(it)
                                }
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .background(
                                            Color(
                                                red = colorHex.substring(0, 2).hexToInt(),
                                                green = colorHex.substring(2, 4).hexToInt(),
                                                blue = colorHex.substring(4, 6).hexToInt()
                                            ),
                                            RoundedCornerShape(16.dp)
                                        )
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .align(CenterHorizontally)
                                )
                                Text(
                                    it.title,
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = false,
                                    modifier = Modifier.align(CenterHorizontally)
                                        .padding(vertical = 4.dp, horizontal = 8.dp),
                                    style = Typography.labelSmall
                                )
                            }

                        }
                    }
                    if (chatListLazyRowState.canScrollBackward) {
                        Spacer(
                            modifier = Modifier.size(64.dp, 88.dp).background(
                                brush = Brush.horizontalGradient(
                                    listOf(applicationColorScheme.surface, Color.Transparent)
                                )
                            )
                                .align(CenterStart)
                        )
                    }
                    if (chatListLazyRowState.canScrollForward) {

                        Spacer(
                            modifier = Modifier.size(64.dp, 88.dp).background(
                                brush = Brush.horizontalGradient(
                                    listOf(Color.Transparent, applicationColorScheme.surface)
                                )
                            )
                                .align(CenterEnd)
                        )
                    }
                }


                Row(Modifier.widthIn(max = 800.dp)) {
                    HorizontalScrollbar(
                        modifier = Modifier
                            .fillMaxWidth(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = chatListLazyRowState
                        )
                    )
                }
            } else {
                controller.getAllChats()
            }*//*

            *//*if (controller.chat != null) {

                Box(modifier = Modifier
                    .widthIn(max = 800.dp)
                    .height(600.dp)
                    .padding(6.dp, 6.dp, 6.dp, 0.dp)) {

                    if (controller.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(48.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.heightIn(420.dp, 640.dp),
                            state = lazyColumnState,
                            reverseLayout = true
                        ) {
                            items(controller.messageList) { messageItem ->

                                val bubbleShape: RoundedCornerShape =
                                    when (controller.messageList.indexOf(messageItem)) {
                                        controller.messageList.lastIndex -> {
                                            if (controller.user!!.name == messageItem.sender)
                                                Shape.LastMessageShapeEnd()
                                            else
                                                Shape.LastMessageShapeStart()
                                        }

                                        0 -> {
                                            if (controller.user!!.name == messageItem.sender)
                                                Shape.FirstMessageShapeEnd()
                                            else
                                                Shape.FirstMessageShapeStart()
                                        }

                                        else -> {
                                            if (controller.user!!.name == messageItem.sender) {
                                                if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) + 1].sender != messageItem.sender
                                                ) {
                                                    Shape.LastMessageShapeEnd()
                                                } else if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) - 1].sender != messageItem.sender
                                                ) {
                                                    Shape.FirstMessageShapeEnd()
                                                } else {
                                                    Shape.MiddleMessageShapeEnd()
                                                }
                                            } else {
                                                if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) + 1].sender != messageItem.sender
                                                ) {
                                                    Shape.LastMessageShapeStart()
                                                } else if (controller.messageList[controller.messageList.indexOf(
                                                        messageItem
                                                    ) - 1].sender != messageItem.sender
                                                ) {
                                                    Shape.FirstMessageShapeStart()
                                                } else {
                                                    Shape.MiddleMessageShapeStart()
                                                }
                                            }
                                        }
                                    }

                                var replyTo: Message? by remember { mutableStateOf(null) }

                                CoroutineScope(Dispatchers.Default).launch {
                                    replyTo = controller.getMessageById(messageItem.replyTo?: -1)
                                }

                                MessageBubble(
                                    message = messageItem,
                                    shape = bubbleShape,
                                    modifier = Modifier
                                        .background(
                                            applicationColorScheme.secondaryContainer.copy(0.6f),
                                            bubbleShape
                                        ),
                                    arrangement = if (controller.user!!.name == messageItem.sender) {
                                        Arrangement.End
                                    } else {
                                        Arrangement.Start
                                    },
                                    withName = (controller.user!!.name != messageItem.sender),
                                    nameColor = Color(
                                        red = chatColorHex.substring(0, 2).hexToInt(),
                                        green = chatColorHex.substring(2, 4).hexToInt(),
                                        blue = chatColorHex.substring(4, 6).hexToInt()
                                    ),
                                    replyTo = replyTo,
                                    onReply = { replyMessage.value = it }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.Companion.verticalGradient(
                                    listOf(
                                        applicationColorScheme.secondaryContainer.copy(0.3f),
                                        applicationColorScheme.secondaryContainer.copy(0f)
                                    )
                                ),
                            )
                    ) {
                        Column(modifier = Modifier.align(TopCenter).padding(4.dp)) {

                            Row(modifier = Modifier.align(CenterHorizontally)) {
                                Box(
                                    modifier = Modifier.background(
                                        Color(
                                            red = chatColorHex.substring(0, 2).hexToInt(),
                                            green = chatColorHex.substring(2, 4).hexToInt(),
                                            blue = chatColorHex.substring(4, 6).hexToInt()
                                        ),
                                        CircleShape
                                    )
                                        .size(56.dp)
                                )

                                Text(
                                    controller.chat!!.title!!,
                                    modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically)
                                )

                            }

                        }
                    }
                }

                Spacer(Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .widthIn(max = 800.dp)
                        .wrapContentHeight()
                        .padding(6.dp, 0.dp, 6.dp, 6.dp)
                ) {
                    MessageInputTextField(
                        backgroundColor = applicationColorScheme.secondaryContainer.copy(0.3f),
                        fontColor = contentColorFor(applicationColorScheme.secondaryContainer.copy(0.3f)),
                        onSend = {
                            controller.sendMessage(it, replyMessage.value?.id)
                        },
                        replyMessageColor = Color(
                            red = chatColorHex.substring(0, 2).hexToInt(),
                            green = chatColorHex.substring(2, 4).hexToInt(),
                            blue = chatColorHex.substring(4, 6).hexToInt()
                        ),
                        replyMessage = replyMessage.value,
                        onReplyClose = { replyMessage.value = null }
                    )
                }
            }*//*
        }


    }*/
}