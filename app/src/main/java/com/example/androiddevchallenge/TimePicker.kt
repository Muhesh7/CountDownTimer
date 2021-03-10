package com.example.androiddevchallenge

import android.graphics.DashPathEffect
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.androiddevchallenge.Constants.Hours
import com.example.androiddevchallenge.Constants.Minutes
import com.example.androiddevchallenge.Constants.Seconds
import com.example.androiddevchallenge.ui.theme.purple200
import com.example.androiddevchallenge.ui.theme.purple500
import com.example.androiddevchallenge.ui.theme.shapes
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun TimePicker(
    viewModel: CounterViewModel
) {
    val second = remember { mutableStateOf(0) }
    val minute = remember { mutableStateOf(0) }
    val hour = remember { mutableStateOf(0) }
    Dialog(
        onDismissRequest = { viewModel.isDialogOn.value = false },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            Text(
                text = "Select Duration",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp),
                color = purple200,
                style = TextStyle(fontSize = typography.h5.fontSize)

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = { hour.value = circularList(hour.value + 1, Hours.size) },
                        Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.KeyboardArrowUp, "Increase", tint = purple200)
                    }
                    Box(
                        Modifier
                            .padding(5.dp)
                            .background(Color.LightGray, shapes.medium)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        Text(
                            text = getFormattedTimeUnit(Hours[hour.value]) + "h",
                            Modifier.padding(15.dp),
                            style = TextStyle(fontSize = 20.sp),
                            color = purple500
                        )
                    }
                    IconButton(
                        onClick = { hour.value = circularList(hour.value - 1, Hours.size) },
                        Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, "Decrease", tint = purple200)
                    }
                }
                Text(
                    text = ":",
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.LightGray,
                    style = TextStyle(fontSize = 40.sp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = { minute.value = circularList(minute.value + 1, Minutes.size) },
                        Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.KeyboardArrowUp, "Increase", tint = purple200)
                    }
                    Box(
                        Modifier
                            .padding(5.dp)
                            .background(Color.LightGray, shapes.medium)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        Text(
                            text = getFormattedTimeUnit(Minutes[minute.value]) + "m",
                            Modifier.padding(15.dp),
                            style = TextStyle(fontSize = 20.sp),
                            color = purple500
                        )
                    }
                    IconButton(
                        onClick = { minute.value = circularList(minute.value - 1, Minutes.size) },
                        Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, "Decrease", tint = purple200)
                    }
                }
                Text(
                    text = ":",
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.LightGray,
                    style = TextStyle(fontSize = 40.sp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = { second.value = circularList(second.value + 1, Seconds.size) },
                        Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.KeyboardArrowUp, "Increase", tint = purple200)
                    }
                    Box(
                        Modifier
                            .padding(5.dp)
                            .background(Color.LightGray, shapes.medium)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        Text(
                            text = getFormattedTimeUnit(Seconds[second.value]) + "s",
                            Modifier.padding(15.dp),
                            style = TextStyle(fontSize = 20.sp),
                            color = purple500
                        )
                    }
                    IconButton(
                        onClick = { second.value = circularList(second.value - 1, Seconds.size) },
                        Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, "Decrease", tint = purple200)
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth(1f)
                    .padding(7.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.isDialogOn.value = false },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(4.dp),
                    border = BorderStroke(0.dp, Color.Transparent)
                ) {
                    Text(text = "Cancel")
                }

                OutlinedButton(
                    onClick = {
                        viewModel.setTime(toMillis(hour.value, minute.value, second.value))
                            .invokeOnCompletion {
                                CountDownService.totalTimerInMillis.postValue(toMillis(hour.value, minute.value, second.value))
                                CountDownService.timerInMillis.postValue(toMillis(hour.value, minute.value, second.value))
                                viewModel.isDialogOn.value = false
                            }

                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(4.dp),
                    border = BorderStroke(0.dp, Color.Transparent)
                ) {
                    Text(text = "Set")
                }
            }
        }
    }

}

@Composable
fun CircularBar(
    modifier:Modifier
) {
    Canvas(modifier = Modifier,
        onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val canvasRadius = size.minDimension / 2.2f
            val strokeWidth = 30f
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 180f,
                topLeft = Offset(
                    x = (canvasWidth / 2) - canvasRadius,
                    y = (canvasHeight / 2) - canvasRadius
                ),
                style = Stroke(strokeWidth),
                useCenter = false,
                size = Size(2 * canvasRadius, 2 * canvasRadius),
                blendMode = BlendMode.Softlight
            )
            drawCircle(
                color = Color.Blue,
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = canvasRadius,
                style = Stroke(strokeWidth),
                blendMode = BlendMode.Softlight
            )
        }
    )
}