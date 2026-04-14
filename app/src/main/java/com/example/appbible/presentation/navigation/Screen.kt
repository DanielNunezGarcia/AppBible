package com.example.appbible.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Lectura : Screen("lectura")
    object Juegos : Screen("juegos")
    object Trivia : Screen("juegos/trivia")
    object FillVerse : Screen("juegos/fillverse")
    object Memorize : Screen("juegos/memorize")
    object Retos : Screen("retos")
    object Progreso : Screen("progreso")
    object DailyQuestion : Screen("dailyquestion")
    
    companion object {
        val bottomNavRoutes = listOf(Home.route, Lectura.route, Retos.route, Progreso.route)
    }
}
