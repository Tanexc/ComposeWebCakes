package presentation.features.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.util.Screen
import presentation.features.chat.ChatScreen
import presentation.features.info.InfoScreen
import presentation.style.strings.Locale
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.getTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMainApp() {
    val theme = getTheme(true)
    val selectedItem: MutableState<Screen> = remember { mutableStateOf(Screen.Info) }

    val items = listOf(
        Screen.Info,
        Screen.FeedBack,
        Screen.Chat
    )

    Scaffold {
        Row {
            Column {
                NavigationRail(
                    modifier = Modifier
                        .background(theme.secondaryContainer)
                ) {
                    items.forEach {
                        NavigationRailItem(
                            modifier = Modifier
                                .padding(8.dp),
                            selected = it.label == selectedItem.value.label,
                            icon = {
                                Icon(
                                    if (it.label == selectedItem.value.label) {
                                        it.iconFilled
                                    } else {
                                        it.iconOutlined
                                    },
                                    contentDescription = Locale(it.label)
                                )
                            },
                            label = {
                                Text(
                                    Locale(it.label),
                                    fontSize = Typography.labelSmall.fontSize,
                                    fontWeight =
                                    if (it.label == selectedItem.value.label) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Medium
                                    }
                                )
                            },
                            onClick = {
                                selectedItem.value = it
                            },
                            alwaysShowLabel = true
                        )

                    }
                }
            }
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(theme.outline)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp, 0.dp)
            ) {
                when(selectedItem.value) {
                    is Screen.Info -> {
                        InfoScreen()
                    }
                    is Screen.Chat -> {
                        ChatScreen()
                    }
                    is Screen.FeedBack -> {
                        Text("FEEDBACK")
                    }
                }
            }
        }

    }
}