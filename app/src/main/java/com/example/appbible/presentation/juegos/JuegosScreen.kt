package com.example.appbible.presentation.juegos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appbible.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegosScreen(
    onNavigateToTrivia: () -> Unit,
    onNavigateToFillVerse: () -> Unit,
    onNavigateToMemorize: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎮 Juegos Bíblicos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PergaminoClaro,
                    titleContentColor = MarronTexto
                )
            )
        },
        containerColor = PergaminoFondo
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Elige un juego",
                style = MaterialTheme.typography.headlineSmall,
                color = MarronTexto
            )
            
            GameCard(
                title = "Trivia Bíblica",
                description = "100 preguntas sobre la Biblia",
                dificultad = "3 niveles",
                onClick = onNavigateToTrivia
            )
            
            GameCard(
                title = "Completa el Versículo",
                description = "Llena los espacios faltantes",
                dificultad = "50 versículos",
                onClick = onNavigateToFillVerse
            )
            
            GameCard(
                title = "Memorización",
                description = "Aprende versículos de memoria",
                dificultad = "5 niveles",
                onClick = onNavigateToMemorize
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = MarronClaro.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🔜 Próximamente",
                        style = MaterialTheme.typography.titleMedium,
                        color = MarronTexto
                    )
                    Text(
                        text = "Ahorcado • Sopa de Letras • Emparejar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronClaro
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameCard(
    title: String,
    description: String,
    dificultad: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = DoradoPrimario,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MarronTexto
                )
                Text(
                    text = dificultad,
                    style = MaterialTheme.typography.bodySmall,
                    color = MarronClaro
                )
            }
            Text("▶️", style = MaterialTheme.typography.headlineMedium)
        }
    }
}