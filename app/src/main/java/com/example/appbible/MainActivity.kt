package com.example.appbible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appbible.presentation.home.HomeScreen
import com.example.appbible.presentation.lectura.LecturaScreen
import com.example.appbible.presentation.theme.AppBibleTheme
import com.example.appbible.presentation.theme.PergaminoClaro
import com.example.appbible.presentation.theme.MarronClaro
import com.example.appbible.presentation.theme.DoradoPrimario
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBibleTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = PergaminoClaro
                        ) {
                            NavigationBarItem(
                                selected = currentRoute == "home",
                                onClick = { if (currentRoute != "home") navController.navigate("home") },
                                icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio", modifier = Modifier.size(24.dp)) },
                                label = { Text("Inicio") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DoradoPrimario,
                                    selectedTextColor = DoradoPrimario,
                                    unselectedIconColor = MarronClaro,
                                    unselectedTextColor = MarronClaro
                                )
                            )
                            NavigationBarItem(
                                selected = currentRoute == "lectura",
                                onClick = { if (currentRoute != "lectura") navController.navigate("lectura") },
                                icon = { Icon(Icons.Filled.MenuBook, contentDescription = "Lectura", modifier = Modifier.size(24.dp)) },
                                label = { Text("Lectura") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DoradoPrimario,
                                    selectedTextColor = DoradoPrimario,
                                    unselectedIconColor = MarronClaro,
                                    unselectedTextColor = MarronClaro
                                )
                            )
                            NavigationBarItem(
                                selected = currentRoute == "juegos",
                                onClick = { if (currentRoute != "juegos") navController.navigate("juegos") },
                                icon = { Icon(Icons.Filled.LocalFireDepartment, contentDescription = "Juegos", modifier = Modifier.size(24.dp)) },
                                label = { Text("Juegos") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DoradoPrimario,
                                    selectedTextColor = DoradoPrimario,
                                    unselectedIconColor = MarronClaro,
                                    unselectedTextColor = MarronClaro
                                )
                            )
                            NavigationBarItem(
                                selected = currentRoute == "retos",
                                onClick = { if (currentRoute != "retos") navController.navigate("retos") },
                                icon = { Icon(Icons.Filled.EmojiEvents, contentDescription = "Retos", modifier = Modifier.size(24.dp)) },
                                label = { Text("Retos") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DoradoPrimario,
                                    selectedTextColor = DoradoPrimario,
                                    unselectedIconColor = MarronClaro,
                                    unselectedTextColor = MarronClaro
                                )
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                onNavigateToLectura = { navController.navigate("lectura") },
                                onNavigateToJuegos = { navController.navigate("juegos") },
                                onNavigateToRetos = { navController.navigate("retos") },
                                onNavigateToProgreso = { navController.navigate("progreso") }
                            )
                        }
                        composable("lectura") {
                            LecturaScreen()
                        }
                        composable("juegos") {
                            Text("Juegos proximamente...")
                        }
                        composable("retos") {
                            Text("Retos proximamente...")
                        }
                        composable("progreso") {
                            Text("Progreso proximamente...")
                        }
                    }
                }
            }
        }
    }
}