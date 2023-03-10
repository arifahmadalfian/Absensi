package com.arifahmadalfian.absensi.features.auth.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arifahmadalfian.absensi.features.auth.ui.PhoneAuthScreen
import com.arifahmadalfian.absensi.ui.theme.AbsensiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbsensiTheme {
                Surface() {
                    Scaffold {
                        //    AuthScreen()
                        PhoneAuthScreen(activity = this)
                    }
                }
            }
        }
    }
}

