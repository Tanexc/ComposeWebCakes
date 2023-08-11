package presentation.features.settings

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.util.Dialogs
import domain.model.User
import org.jetbrains.compose.web.dom.Progress
import org.koin.core.context.GlobalContext
import presentation.features.info.components.InfoCard
import presentation.features.settings.components.RegistrationForm
import presentation.features.settings.components.SignInForm
import presentation.features.settings.controller.SettingsController
import presentation.features.util.widgets.dialogs.BaseDialog
import presentation.style.strings.Strings.administrator
import presentation.style.strings.Strings.appName
import presentation.style.strings.Strings.client
import presentation.style.strings.Strings.enter
import presentation.style.strings.Strings.error
import presentation.style.strings.Strings.logout
import presentation.style.strings.Strings.registration
import presentation.style.strings.Strings.typeName
import presentation.style.strings.Strings.useDarkTheme
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme
import presentation.style.ui.theme.applicationUseDarkTheme


@Composable
fun SettingsScreen(
    onUserUpdate: (User?) -> Unit,
    user: User?
) {
    val controller: SettingsController by GlobalContext.get().inject()
    controller.setUser(user)

    val lazyColumnState = rememberLazyListState()
    var clientNameFieldValue by remember { mutableStateOf("") }

    var showSignInForm by remember {
        mutableStateOf(false)
    }

    var showRegisterForm by remember {
        mutableStateOf(false)
    }

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
                                        .align(End),
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

            if (!(showRegisterForm || showSignInForm)) {
                item {
                    Column(
                        modifier = Modifier.padding(0.dp, 4.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = if (controller.user == null) {
                                applicationResources(client)
                            } else {
                                applicationResources(administrator)
                            },
                            modifier = Modifier
                                .padding(0.dp, 4.dp)
                                .align(Start),
                            style = Typography.titleLarge
                        )
                        if (controller.user == null) {
                            if (controller.clientName == null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    OutlinedTextField(
                                        value = clientNameFieldValue,
                                        onValueChange = { clientNameFieldValue = it },
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
                                            controller.updateClientName(clientNameFieldValue)
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
                                    text = controller.user?.let { "${it.name} ${it.surname}" }
                                        ?: applicationResources(
                                            appName
                                        ),
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

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    if (controller.stateProcessing) {
                        Row(modifier = Modifier.align(End)) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Center)
                                        .size(48.dp)
                                )
                            }
                        }
                    } else if (controller.user == null) {
                        Row(modifier = Modifier.align(End)) {
                            Text(
                                text = applicationResources(registration),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        showSignInForm = false
                                        showRegisterForm = true
                                    },
                            )
                            Text(
                                text = applicationResources(enter),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        showRegisterForm = false
                                        showSignInForm = true
                                    }
                            )
                        }

                        if (showRegisterForm) {
                            Row(modifier = Modifier.align(End)) {
                                RegistrationForm(
                                    onSubmit = { name, surname, login, password ->
                                        controller.signUpUser(
                                            login = login,
                                            password = password,
                                            name = name,
                                            surname = surname,
                                            onUserUpdate = {
                                                showRegisterForm = false
                                                if (it == null) {
                                                    showRegisterForm = true
                                                }
                                                onUserUpdate(it)
                                            }
                                        )

                                    }
                                )
                            }
                        } else if (showSignInForm) {
                            Row(modifier = Modifier.align(End)) {
                                SignInForm(
                                    onSubmit = { login, password ->

                                        controller.signInUser(
                                            login = login,
                                            password = password,
                                            onUserUpdate = {
                                                showSignInForm = false
                                                if (it == null) {
                                                    showSignInForm = true
                                                }
                                                onUserUpdate(it)
                                            }
                                        )

                                    }
                                )
                            }
                        }
                    } else {
                        Row(modifier = Modifier.align(End)) {
                            Text(
                                text = applicationResources(logout),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        showRegisterForm = false
                                        showSignInForm = false
                                        controller.exitUser(
                                            onUserUpdate = {
                                                onUserUpdate(it)
                                            }
                                        )
                                    }
                            )
                        }
                    }
                }
            }
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