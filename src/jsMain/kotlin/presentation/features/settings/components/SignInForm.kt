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
import presentation.style.icons.filled.IconFilledVisibility
import presentation.style.icons.filled.IconFilledVisibilityOff
import presentation.style.strings.Strings
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import util.validators.PasswordValidator.isValid

@Composable
fun SignInForm(
    onSubmit: (login: String, password: String) -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val wrongPassword by remember { mutableStateOf(false) }

    val isSubmited by remember { mutableStateOf(false) }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by remember {
        derivedStateOf {
             login != "" && password != ""
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            applicationResources(Strings.enter),
            style = Typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
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
                if (login.isEmpty()) Text(applicationResources(Strings.requiredField))
            },
            modifier = Modifier.width(TextFieldDefaults.MinWidth),
            trailingIcon = {
                if (login.isNotBlank())
                    IconButton(onClick = { login = "" }) {
                        Icon(Icons.Filled.Clear, null)
                    }
            }
        )
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
                        if (password.isEmpty()) Text(applicationResources(Strings.requiredField))
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

        }

        Spacer(Modifier.size(32.dp))
        Button(
            enabled = isFormValid,
            onClick = {
                onSubmit(login, password)
            }
        ) {
            Text(applicationResources(Strings.send))
        }
    }
}