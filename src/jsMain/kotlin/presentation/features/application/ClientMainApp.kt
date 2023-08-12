package presentation.features.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.util.Screen
import presentation.features.application.controller.MainController
import presentation.features.chat.chatScreen.ChatScreen
import presentation.features.chat.clientChatScreen.ChatScreen
import presentation.features.info.InfoScreen
import presentation.features.settings.SettingsScreen
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.ClientTheme
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme
import presentation.style.ui.theme.applicationUseDarkTheme


@Composable
fun ClientMainApp() {
    val controller = remember { MainController() }

    ClientTheme(applicationUseDarkTheme) {
        Scaffold {
            Row {
                Column {
                    NavigationRail(
                        modifier = Modifier
                            .background(applicationColorScheme.secondaryContainer)
                    ) {
                        controller.items.forEach {
                            NavigationRailItem(
                                modifier = Modifier
                                    .padding(8.dp),
                                selected = controller.currentScreen == it,
                                icon = {
                                    Icon(
                                        if (it.label == controller.currentScreen.label) {
                                            it.iconFilled
                                        } else {
                                            it.iconOutlined
                                        },
                                        contentDescription = applicationResources(it.label)
                                    )
                                },
                                label = {
                                    Text(
                                        applicationResources(it.label),
                                        fontSize = Typography.labelSmall.fontSize,
                                        fontWeight =
                                        if (it.label == controller.currentScreen.label) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Medium
                                        }
                                    )
                                },
                                onClick = {
                                    controller.updateScreen(it)
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
                            .background(applicationColorScheme.outline)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize().padding(12.dp, 0.dp)
                ) {
                    when(controller.currentScreen) {
                        is Screen.Info -> {
                            InfoScreen()
                        }
                        is Screen.Chat -> {
                            if (controller.user != null)
                            ChatScreen(controller.user!!)
                            else ChatScreen()
                        }
                        is Screen.FeedBack -> {
                            Text("FEEDBACK")
                        }

                        is Screen.Settings -> {
                            SettingsScreen(
                                onUserUpdate = {controller.updateUser(it)},
                                null
                            )
                        }
                    }
                }
            }

        }
    }

}