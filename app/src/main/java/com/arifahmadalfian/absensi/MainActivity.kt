package com.arifahmadalfian.absensi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arifahmadalfian.absensi.presentation.QrScreen
import com.arifahmadalfian.absensi.ui.theme.AbsensiTheme

class MainActivity : ComponentActivity() {

    var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreen
        }

        setContent {
            MainScreen {
                keepSplashScreen = false
            }
        }
    }
}

@Composable
fun MainScreen(onDataLoad: () -> Unit) {
    var fakeLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = Unit) {
        fakeLoading = false
        onDataLoad()
    }

    if (!fakeLoading) {
        AbsensiTheme {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                QrScreen()
            }
        }
    }
}
