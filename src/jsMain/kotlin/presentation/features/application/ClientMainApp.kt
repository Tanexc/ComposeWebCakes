package presentation.features.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.util.Screen
import domain.controller.SettingsController
import presentation.features.application.controller.SettingsControllerImpl
import presentation.features.chat.chatScreen.ChatScreen
import presentation.features.info.InfoScreen
import presentation.style.ui.theme.Typography


@Composable
fun ClientMainApp() {
    val settingsController: SettingsController = SettingsControllerImpl()

    val items = listOf(
        Screen.Info,
        Screen.FeedBack,
        Screen.Chat
    )

    val selectedItem: MutableState<Screen> = remember { mutableStateOf(Screen.Info) }

    Scaffold {
        Row {
            Column {
                NavigationRail(
                    modifier = Modifier
                        .background(settingsController.theme.secondaryContainer)
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
                                    contentDescription = settingsController.locale(it.label)
                                )
                            },
                            label = {
                                Text(
                                    settingsController.locale(it.label),
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
                        .background(settingsController.theme.outline)
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