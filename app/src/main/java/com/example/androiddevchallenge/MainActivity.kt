/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.androiddevchallenge.Constants.ACTION_START_SERVICE
import com.example.androiddevchallenge.Constants.ACTION_STOP_SERVICE
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.purple200
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val counterViewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MainApp(viewModel = counterViewModel, this)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: CounterViewModel, @ActivityContext context: MainActivity) {
    val fabShape = CircleShape
    val text = remember { mutableStateOf(0L) }
    val totalTime = remember { mutableStateOf(1L) }
    CountDownService.totalTimerInMillis.observe(context, {
        if (it != null) {
            totalTime.value = it
        }
    })
    CountDownService.timerInMillis.observe(context, {
        if (it != null) {
            text.value = it
        }
    })
    val timeEvent = remember { mutableStateOf(TimeEvent.DEFAULT) }
    CountDownService.timerEvent.observe(context, {
        timeEvent.value = it
    })
    viewModel.getTime()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "CountDownTimer") }
            )
        },
        bottomBar = {
            BottomAppBar(cutoutShape = fabShape) {
                if (timeEvent.value != TimeEvent.PAUSE) {
                    IconButton(onClick = {
                        if (timeEvent.value == TimeEvent.START) {
                            CountDownService.timerEvent.value = TimeEvent.PAUSE
                        }
                    }, Modifier.weight(1f))
                    {
                        Icon(
                            Icons.Default.Pause, "Pause",
                            Modifier
                                .background(purple200, fabShape)
                                .padding(15.dp)
                        )
                    }
                } else {
                    IconButton(onClick = {
                        CountDownService.timerEvent.postValue(TimeEvent.START)
                    }, Modifier.weight(1f))
                    {
                        Icon(
                            Icons.Default.PlayArrow, "Resume",
                            Modifier
                                .background(purple200, fabShape)
                                .padding(15.dp)
                        )
                    }
                }
                IconButton(
                    onClick = { CountDownService.timerInMillis.value = totalTime.value },
                    Modifier.weight(1f)
                )
                {
                    Icon(
                        Icons.Filled.Replay, "Reset",
                        Modifier
                            .background(purple200, fabShape)
                            .padding(15.dp)
                    )
                }
            }
        },
        content = {
            viewModel.getTime()
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (barRef, textRef, detailRef) = createRefs()
                val width = context.resources.configuration.screenWidthDp
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(width.dp)
                        .padding(45.dp)
                        .constrainAs(barRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    CircularProgressIndicator(
                        progress = 1f,
                        modifier = Modifier
                            .matchParentSize()
                            .align(Alignment.Center),
                        color = Color.LightGray,
                        strokeWidth = 15.dp,
                    )
                    CircularProgressIndicator(
                        progress = (text.value.toFloat() / totalTime.value.toFloat()),
                        modifier = Modifier
                            .matchParentSize()
                            .align(Alignment.Center),
                        color = purple200,
                        strokeWidth = 15.dp,
                    )
                }

                Text(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .constrainAs(textRef) {
                            top.linkTo(barRef.top)
                            bottom.linkTo(barRef.bottom)
                            start.linkTo(barRef.start)
                            end.linkTo(barRef.end)
                        },
                    textAlign = TextAlign.Center,
                    text = getFormattedTime(text.value, false),
                    style = TextStyle(fontSize = 50.sp)
                )
                Row(modifier = Modifier
                    .constrainAs(detailRef) {
                        top.linkTo(barRef.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(20.dp)
                )
                {
                    FloatingActionButton(
                        onClick = {
                            if(timeEvent.value == TimeEvent.START)
                            {
                                CountDownService.timerInMillis.value = text.value - 10000L
                                CountDownService.totalTimerInMillis.value = totalTime.value - 10000L
                            }
                        }, Modifier
                            .align(Alignment.CenterVertically)
                            .wrapContentWidth(),
                        contentColor = Color.White,
                        backgroundColor = purple200,
                        content = { Icon(Icons.Default.Replay10, "-10s") }
                    )


                    ExtendedFloatingActionButton(
                        text = { Text(text = "Set Time") },
                        icon = {
                            Icon(
                                Icons.Default.Timer,
                                "Timer"
                            )
                        },
                        onClick = { viewModel.isDialogOn.value = !viewModel.isDialogOn.value },
                        contentColor = Color.White,
                        modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(20.dp),
                        backgroundColor = purple200
                    )
                    FloatingActionButton(
                        onClick = {
                            if(timeEvent.value == TimeEvent.START)
                            {
                                CountDownService.timerInMillis.value = text.value + 10000L
                                CountDownService.totalTimerInMillis.value = totalTime.value + 10000L
                            }
                        }, Modifier
                            .align(Alignment.CenterVertically)
                            .wrapContentWidth(),
                        contentColor = Color.White,
                        backgroundColor = purple200,
                        content = { Icon(Icons.Default.Forward10, "+10s") }
                    )

                }
                if (viewModel.isDialogOn.value) {
                    TimePicker(viewModel)
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (timeEvent.value == TimeEvent.DEFAULT) {
                FloatingActionButton(
                    onClick =
                    {
                        if (totalTime.value == 1L) Toast.makeText(
                            context,
                            "Enter your duration",
                            Toast.LENGTH_SHORT
                        ).show()
                        else sendCommandToService(ACTION_START_SERVICE, context)

                    },
                    contentColor = Color.White,
                    backgroundColor = purple200,
                    content = { Icon(Icons.Default.ArrowForward, "Add") }
                )
            } else if (timeEvent.value != TimeEvent.END) {
                FloatingActionButton(
                    onClick = {
                        sendCommandToService(ACTION_STOP_SERVICE, context)
                        Toast.makeText(context, "CountDown Stopped", Toast.LENGTH_SHORT).show()
                    },
                    contentColor = Color.White,
                    backgroundColor = purple200,
                    content = { Icon(Icons.Default.Stop, "STop") }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}
