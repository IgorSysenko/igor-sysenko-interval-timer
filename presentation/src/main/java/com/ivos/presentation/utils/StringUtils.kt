package com.ivos.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ivos.presentation.R

@Composable
fun getIntervalString(
    intervalCount: Int,
) = "$intervalCount ${stringResource(when (intervalCount) {
    1 -> R.string.intervals_list_count_1
    2, 3, 4 -> R.string.intervals_list_count_2_4
    else ->  R.string.intervals_list_count_many
})}"
