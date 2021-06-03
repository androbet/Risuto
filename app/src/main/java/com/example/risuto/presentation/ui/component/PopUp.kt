package com.example.risuto.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.risuto.data.local.model.WatchStatus

@Composable
fun MyAnimePopUp(
    setScore: (Int) -> Unit,
    setWatchStatus: (WatchStatus) -> Unit,
    onDoneClicked: () -> Unit
) {
    Popup(alignment = Alignment.Center) {
        Box(modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colors.background)
        ) {
            Column(modifier = Modifier.padding(16.dp)){
                var score by remember { mutableStateOf(-1) }
                var watchState by remember { mutableStateOf(WatchStatus.Default) }
                var expandedWatchStatus by remember { mutableStateOf(false) }
                var expandedScore by remember { mutableStateOf(false) }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { expandedScore = true }) {
                    DropdownMenu(expanded = expandedScore, onDismissRequest = { expandedScore = false }) {
                        for(i in 10 downTo 1) {
                            DropdownMenuItem(onClick = {
                                score = i
                                expandedScore = false
                            }) {
                                Text(i.toString())
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { expandedWatchStatus = true }) {
                    DropdownMenu(expanded = expandedWatchStatus, onDismissRequest = { expandedWatchStatus = false }) {
                        DropdownMenuItem(onClick = {
                            watchState = WatchStatus.PlanToWatch
                            expandedWatchStatus = false
                        }) {
                            Text("Plan To Watch")
                        }
                        DropdownMenuItem(onClick = {
                            watchState = WatchStatus.Completed
                            expandedWatchStatus = false
                        }) {
                            Text("Completed")
                        }
                        DropdownMenuItem(onClick = {
                            watchState = WatchStatus.Watching
                            expandedWatchStatus = false
                        }) {
                            Text("Watching")
                        }
                        DropdownMenuItem(onClick = {
                            watchState = WatchStatus.Dropped
                            expandedWatchStatus = false
                        }) {
                            Text("Dropped")
                        }
                        DropdownMenuItem(onClick = {
                            watchState = WatchStatus.OnHold
                            expandedWatchStatus = false
                        }) {
                            Text("On Hold")
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .clickable {
                            setScore(score)
                            setWatchStatus(watchState)
                            onDoneClicked()
                        },
                    text = "Done"
                )
            }
        }

    }
}