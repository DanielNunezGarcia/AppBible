package com.example.appbible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appbible.presentation.dailyquestion.DailyQuestionScreen
import com.example.appbible.presentation.home.HomeScreen
import com.example.appbible.presentation.juegos.JuegosScreen
import com.example.appbible.presentation.juegos.fillverse.FillVerseScreen
import com.example.appbible.presentation.juegos.memorize.MemorizeScreen
import com.example.appbible.presentation.juegos.trivia.TriviaScreen
import com.example.appbible.presentation.theme.*
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
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "home") Icons.Filled.Home else Icons.Outlined.Home,
                                        contentDescription = "Inicio",
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = { Text("Inicio") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MarronClaro,
                                    selectedTextColor = MarronClaro,
                                    unselectedIconColor = MarronClaro.copy(alpha = 0.5f),
                                    unselectedTextColor = MarronClaro.copy(alpha = 0.5f),
                                    indicatorColor = MarronClaro.copy(alpha = 0.2f)
                                )
                            )
                            NavigationBarItem(
                                selected = currentRoute == "lectura",
                                onClick = { if (currentRoute != "lectura") navController.navigate("lectura") },
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "lectura") Icons.AutoMirrored.Filled.MenuBook else Icons.AutoMirrored.Outlined.MenuBook,
                                        contentDescription = "Lectura",
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = { Text("Lectura") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MarronClaro,
                                    selectedTextColor = MarronClaro,
                                    unselectedIconColor = MarronClaro.copy(alpha = 0.5f),
                                    unselectedTextColor = MarronClaro.copy(alpha = 0.5f),
                                    indicatorColor = MarronClaro.copy(alpha = 0.2f)
                                )
                            )
                            NavigationBarItem(
                                selected = currentRoute == "juegos",
                                onClick = { if (currentRoute != "juegos") navController.navigate("juegos") },
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "juegos") Icons.Filled.LocalFireDepartment else Icons.Outlined.LocalFireDepartment,
                                        contentDescription = "Juegos",
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = { Text("Juegos") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MarronClaro,
                                    selectedTextColor = MarronClaro,
                                    unselectedIconColor = MarronClaro.copy(alpha = 0.5f),
                                    unselectedTextColor = MarronClaro.copy(alpha = 0.5f),
                                    indicatorColor = MarronClaro.copy(alpha = 0.2f)
                                )
                            )
                            NavigationBarItem(
                                selected = currentRoute == "retos",
                                onClick = { if (currentRoute != "retos") navController.navigate("retos") },
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == "retos") Icons.Filled.EmojiEvents else Icons.Outlined.EmojiEvents,
                                        contentDescription = "Logros",
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = { Text("Logros") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MarronClaro,
                                    selectedTextColor = MarronClaro,
                                    unselectedIconColor = MarronClaro.copy(alpha = 0.5f),
                                    unselectedTextColor = MarronClaro.copy(alpha = 0.5f),
                                    indicatorColor = MarronClaro.copy(alpha = 0.2f)
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
                                onNavigateToProgreso = { navController.navigate("progreso") },
                                onNavigateToDailyQuestion = { navController.navigate("dailyquestion") }
                            )
                        }
                        composable("lectura") {
                            PantallaLectura()
                        }
                        composable("juegos") {
                            JuegosScreen(
                                onNavigateToTrivia = { navController.navigate("trivia") },
                                onNavigateToFillVerse = { navController.navigate("fillverse") },
                                onNavigateToMemorize = { navController.navigate("memorize") },
                                onBack = { navController.navigate("home") }
                            )
                        }
                        composable("fillverse") {
                            FillVerseScreen(
                                onBack = { navController.popBackStack() },
                                onFinish = { navController.popBackStack() }
                            )
                        }
                        composable("memorize") {
                            MemorizeScreen(
                                onBack = { navController.popBackStack() },
                                onFinish = { navController.popBackStack() }
                            )
                        }
                        composable("retos") {
                            PantallaRetos()
                        }
                        composable("progreso") {
                            PantallaProgreso()
                        }
                        composable("trivia") {
                            TriviaScreen(
                                onBack = { navController.popBackStack() },
                                onFinish = { navController.popBackStack() }
                            )
                        }
                        composable("dailyquestion") {
                            DailyQuestionScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaLectura() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Próximamente",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MarronTexto
        )
    }
}

@Composable
fun PantallaProgreso() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Próximamente",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MarronTexto
        )
    }
}

@Composable
fun PantallaRetos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Próximamente",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MarronTexto
        )
    }
}