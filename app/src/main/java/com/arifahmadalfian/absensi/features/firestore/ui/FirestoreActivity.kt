package com.arifahmadalfian.absensi.features.firestore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arifahmadalfian.absensi.ui.theme.AbsensiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirestoreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbsensiTheme() {
                val isInput = remember { mutableStateOf(false)}
                Surface() {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                isInput.value = true
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "")
                            }
                        }
                    ) {
                        FirestoreScreen(isInput)
                    }
                }
            }
        }
    }

}