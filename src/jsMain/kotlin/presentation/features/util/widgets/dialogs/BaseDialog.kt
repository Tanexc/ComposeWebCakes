package presentation.features.util.widgets.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import presentation.style.strings.Strings
import presentation.style.strings.applicationResources
import presentation.style.ui.theme.Typography
import presentation.style.ui.theme.applicationColorScheme

@Composable
fun BaseDialog(
    message: String?,
    icon: ImageVector,
    headlineText: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {

        Box(
            modifier = Modifier
                .widthIn(min = 280.dp, max = 560.dp)
                .wrapContentHeight()
                .heightIn(min = 54.dp)
                .background(
                    applicationColorScheme.surfaceColorAtElevation(3.dp),
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                Icon(
                    icon,
                    null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    tint = applicationColorScheme.secondary
                )

                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    headlineText,
                    style = Typography.headlineSmall,
                    color = applicationColorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                )

                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    message ?: "Unknown error",
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = applicationColorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    applicationResources(Strings.ok),
                    style = Typography.labelLarge,
                    color = applicationColorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            onDismiss()
                        }
                )

            }
        }

    }
}