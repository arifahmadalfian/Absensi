package com.arifahmadalfian.absensi.features.auth.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arifahmadalfian.absensi.features.realtime.ui.RealtimeScreen
import com.arifahmadalfian.absensi.ui.theme.AbsensiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealtimeDatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbsensiTheme {
                val isInsert = remember { mutableStateOf(false)}
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                isInsert.value = true
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "")
                            }
                        }
                    ) {
                        RealtimeScreen(isInsert)
                    }
                }
            }
        }
    }
}