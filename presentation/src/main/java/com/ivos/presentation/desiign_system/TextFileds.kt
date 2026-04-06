package com.ivos.presentation.desiign_system

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivos.presentation.theme.LocalExtraColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutIdInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,

    enabled: Boolean = true,
    isError: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = if (enabled) MaterialTheme.colorScheme.onBackground else LocalExtraColors.current.textSecondary,
        fontSize = 20.sp,
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    ),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = LocalExtraColors.current.border,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        disabledBorderColor = LocalExtraColors.current.disabledBg,
        errorBorderColor = MaterialTheme.colorScheme.error,
    ),
    onDone: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    LaunchedEffect(imeVisible) {
        if (!imeVisible) focusManager.clearFocus()
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
                focusManager.clearFocus()
                keyboard?.hide()
            }
        ),
        colors = colors,
    )
}
