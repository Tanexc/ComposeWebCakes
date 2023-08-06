package presentation.features.settings

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.User
import presentation.features.info.components.InfoCard
import presentation.features.settings.controller.SettingsController
import presentation.style.strings.Strings.client
import presentation.style.strings.Strings.typeName
import presentation.style.strings.Strings.useDarkTheme
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.applicationColorScheme
import presentation.style.ui.theme.applicationUseDarkTheme


@Composable
fun SettingsScreen(
    onUserUpdate: (User) -> Unit,
    user: User?
) {
    val controller = SettingsController(user)

    val lazyColumnState = rememberLazyListState()
    val clientNameFieldValue: MutableState<String> = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 0.dp
                )
                .widthIn(max = 800.dp)
                .align(TopCenter),
            horizontalAlignment = CenterHorizontally,
            state = lazyColumnState,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(0.dp, 4.dp)
                ) {
                    InfoCard(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(
                                horizontal = 0.dp,
                                vertical = 4.dp
                            )
                            .align(CenterHorizontally),
                        borderColor = applicationColorScheme.outline,
                        borderRadius = 16.dp,
                        borderWidth = 1.dp,
                        backgroundColor = applicationColorScheme.secondaryContainer.copy(
                            0.3f
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .wrapContentHeight()
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .wrapContentHeight()
                                    .align(CenterVertically)
                            ) {
                                Text(
                                    applicationResources(useDarkTheme),
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .align(Start)
                                        .padding(8.dp),
                                    fontSize = 16.sp
                                )
                            }
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Switch(
                                    checked = applicationUseDarkTheme,
                                    onCheckedChange = {
                                        controller.updateUseDarkTheme()
                                    },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.End),
                                    thumbContent = {
                                        when (applicationUseDarkTheme) {
                                            true -> Icon(
                                                Icons.Outlined.Check,
                                                null,
                                                modifier = Modifier.padding(4.dp)
                                            )

                                            else -> {}
                                        }

                                    }
                                )
                            }
                        }
                    }
                }

            }
            item {
                Column(
                    modifier = Modifier.padding(0.dp, 4.dp)
                        .fillMaxWidth()
                ) {
                    if (controller.user == null) {
                        Text(
                            text = applicationResources(client),
                            modifier = Modifier
                                .padding(0.dp, 4.dp)
                                .align(Start),
                            fontSize = 22.sp
                        )
                        if (controller.clientName == null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = clientNameFieldValue.value,
                                    onValueChange = { clientNameFieldValue.value = it },
                                    placeholder = {
                                        Text(
                                            applicationResources(typeName)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 72.dp)
                                        .align(CenterStart)
                                )
                                FilledIconButton(
                                    onClick = {
                                        controller.updateClientName(clientNameFieldValue.value)
                                    },
                                    content = {
                                        Icon(
                                            Icons.Filled.Check,
                                            null,
                                            modifier = Modifier
                                                .padding(8.dp)
                                        )
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .size(56.dp)
                                        .align(CenterEnd)
                                )

                            }
                        } else {
                            InfoCard(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(
                                        horizontal = 0.dp,
                                        vertical = 4.dp
                                    )
                                    .align(CenterHorizontally)
                                    .fillMaxWidth(),
                                borderColor = applicationColorScheme.outline,
                                borderRadius = 16.dp,
                                borderWidth = 1.dp,
                                backgroundColor = applicationColorScheme.secondaryContainer.copy(
                                    0.3f
                                )
                            ) {

                                Text(
                                    text = controller.clientName ?: "",
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                            }
                        }
                    }
                }

            }
        }

        VerticalScrollbar(
            modifier = Modifier
                .align(CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = lazyColumnState
            )
        )

    }


}