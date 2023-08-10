package presentation.features.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.User
import presentation.style.icons.filled.IconFilledVisibility
import presentation.style.icons.filled.IconFilledVisibilityOff
import presentation.style.strings.Strings
import presentation.style.strings.Strings.name
import presentation.style.strings.Strings.password
import presentation.style.strings.Strings.registration
import presentation.style.strings.Strings.requiredField
import presentation.style.strings.Strings.surname
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import util.State
import util.validators.PasswordValidator.isValid

@Composable
fun RegistrationForm(
    onSubmit: (name: String, surname: String, login: String, password: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }

    val wrongPassword by remember { mutableStateOf(false) }
    val simplePassword by remember { mutableStateOf(false) }

    val isSubmited by remember { mutableStateOf(false) }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by remember {
        derivedStateOf {
            name != "" && surname != "" && login != "" && password != "" && passwordRepeat == password
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            applicationResources(registration),
            style = Typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it.trim() },
            label = { Text(applicationResources(Strings.name)) },
            singleLine = true,
            isError = name == "" && isSubmited,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (name.isEmpty()) Text(applicationResources(requiredField))
            },
            modifier = Modifier.width(TextFieldDefaults.MinWidth),
            trailingIcon = {
                if (name.isNotBlank())
                    IconButton(onClick = { name = "" }) {
                        Icon(Icons.Filled.Clear, null)
                    }
            }
        )
        Spacer(Modifier.size(8.dp))
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it.trim() },
            label = { Text(applicationResources(Strings.surname)) },
            singleLine = true,
            isError = surname == "" && isSubmited,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (surname.isEmpty()) Text(applicationResources(requiredField))
            },
            modifier = Modifier.width(TextFieldDefaults.MinWidth),
            trailingIcon = {
                if (surname.isNotBlank())
                    IconButton(onClick = { surname = "" }) {
                        Icon(Icons.Filled.Clear, null)
                    }
            }
        )
        Spacer(Modifier.size(8.dp))
        OutlinedTextField(
            value = login,
            onValueChange = { login = it.trim() },
            label = { Text(applicationResources(Strings.login)) },
            singleLine = true,
            isError = login == "" && isSubmited,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (login.isEmpty()) Text(applicationResources(requiredField))
            },
            modifier = Modifier.width(TextFieldDefaults.MinWidth),
            trailingIcon = {
                if (login.isNotBlank())
                    IconButton(onClick = { login = "" }) {
                        Icon(Icons.Filled.Clear, null)
                    }
            }
        )
        AnimatedVisibility(name != "" && surname != "" && login != "") {
            Column {
                Spacer(Modifier.size(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = { Text(applicationResources(Strings.password)) },
                    singleLine = true,
                    isError = isSubmited && (password.isEmpty() || !password.isValid()),
                    supportingText = {
                        if (passwordRepeat.isEmpty()) Text(applicationResources(requiredField))
                        else if (!password.isValid()) Text(applicationResources(Strings.simplePassword))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(
                                if (isPasswordVisible) IconFilledVisibility else IconFilledVisibilityOff,
                                null
                            )
                        }
                    }
                )
                Spacer(Modifier.size(8.dp))
                OutlinedTextField(
                    value = passwordRepeat,
                    onValueChange = { passwordRepeat = it },
                    label = { Text(applicationResources(Strings.repeatPassword)) },
                    singleLine = true,
                    isError = isSubmited && (passwordRepeat.isEmpty() || passwordRepeat != password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    supportingText = {
                        if (passwordRepeat.isEmpty()) Text(applicationResources(requiredField))
                        else if (password != passwordRepeat) Text(applicationResources(Strings.wrongPassword))
                    },
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(
                                if (isPasswordVisible) IconFilledVisibility else IconFilledVisibilityOff,
                                null
                            )
                        }
                    }
                )
            }
        }

        Spacer(Modifier.size(32.dp))
        Button(
            enabled = isFormValid,
            onClick = {
                onSubmit(name, surname, login, password)
            }
        ) {
            Text(applicationResources(Strings.send))
        }
    }
}

