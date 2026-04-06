package com.ivos.presentation.desiign_system

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivos.presentation.theme.LocalExtraColors

@Composable
fun WorkoutScreenTopBar(
    modifier: Modifier = Modifier,
    headerText: String,
    onBackClick: () -> Unit,
    endContent: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    border = BorderStroke(1.5.dp, LocalExtraColors.current.border),
                    shape = MaterialTheme.shapes.extraLarge
                )
                .align(Alignment.CenterStart),
            onClick = onBackClick
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
            )
        }

        Text(
            modifier = Modifier,
            text = headerText,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd),
        ) {
            endContent()
        }
    }
}
