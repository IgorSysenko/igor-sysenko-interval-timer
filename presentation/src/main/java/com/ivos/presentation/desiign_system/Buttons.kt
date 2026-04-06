package com.ivos.presentation.desiign_system

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivos.presentation.theme.LocalExtraColors
import com.ivos.presentation.theme.LocalExtraTypography
import com.ivos.presentation.theme.LocalSpacing

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,

    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContainerColor: Color = LocalExtraColors.current.primaryLight,
    disabledContentColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
    borderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),

    icon: @Composable () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .alpha(if (!enabled) 0.7f else 1f),
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor =  contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        border = if (!enabled) {
            BorderStroke(1.5.dp, borderColor)
        } else null,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = LocalSpacing.current.m)
                    .size(18.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
        }

        icon()

        Text(
            text = text,
            style = LocalExtraTypography.current.button.copy(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
fun GhostButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,

    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .alpha(if (!enabled) 0.7f else 1f),
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor =  contentColor,
        ),
        border = BorderStroke(1.5.dp, borderColor),
    ) {
        Text(
            text = text,
            style = LocalExtraTypography.current.button.copy(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}
