package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.purple200

@Composable
fun CircularCountDownBar(progress:MutableState<Float>) {
    Box {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.LightGray,
            strokeWidth = 10.dp,
        )
        CircularProgressIndicator(
            progress = progress.value,
            modifier = Modifier
                .fillMaxWidth(),
            color = purple200,
            strokeWidth = 10.dp,
        )
    }
}

