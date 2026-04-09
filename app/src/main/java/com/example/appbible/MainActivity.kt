package com.example.appbible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.appbible.presentation.navigation.AppNavigation
import com.example.appbible.presentation.navigation.BottomNavBar
import com.example.appbible.presentation.theme.AppBibleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppBibleTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(navController = navController)
                    BottomNavBar(navController = navController)
                }
            }
        }
    }
}