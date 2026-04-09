package com.example.appbible.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appbible.presentation.home.HomeScreen
import com.example.appbible.presentation.juegos.JuegosScreen
import com.example.appbible.presentation.juegos.trivia.TriviaScreen
import com.example.appbible.presentation.juegos.fillverse.FillVerseScreen
import com.example.appbible.presentation.juegos.memorize.MemorizeScreen
import com.example.appbible.presentation.lectura.LecturaScreen
import com.example.appbible.presentation.progreso.ProgresoScreen
import com.example.appbible.presentation.retos.RetosScreen
import com.example.appbible.presentation.navigation.Screen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToLectura = { navController.navigate(Screen.Lectura.route) },
                onNavigateToJuegos = { navController.navigate(Screen.Juegos.route) },
                onNavigateToRetos = { navController.navigate(Screen.Retos.route) },
                onNavigateToProgreso = { navController.navigate(Screen.Progreso.route) }
            )
        }

        composable(Screen.Lectura.route) {
            LecturaScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Juegos.route) {
            JuegosScreen(
                onNavigateToTrivia = { navController.navigate(Screen.Trivia.route) },
                onNavigateToFillVerse = { navController.navigate(Screen.FillVerse.route) },
                onNavigateToMemorize = { navController.navigate(Screen.Memorize.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Trivia.route) {
            TriviaScreen(
                onBack = { navController.popBackStack() },
                onFinish = { navController.popBackStack() }
            )
        }

        composable(Screen.FillVerse.route) {
            FillVerseScreen(
                onBack = { navController.popBackStack() },
                onFinish = { navController.popBackStack() }
            )
        }

        composable(Screen.Memorize.route) {
            MemorizeScreen(
                onBack = { navController.popBackStack() },
                onFinish = { navController.popBackStack() }
            )
        }

        composable(Screen.Retos.route) {
            RetosScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Progreso.route) {
            ProgresoScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}