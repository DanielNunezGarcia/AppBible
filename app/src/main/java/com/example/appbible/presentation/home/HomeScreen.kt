package com.example.appbible.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appbible.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToLectura: () -> Unit,
    onNavigateToJuegos: () -> Unit,
    onNavigateToRetos: () -> Unit,
    onNavigateToProgreso: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AppBible ✨ Puntos: ${uiState.puntos}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
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
                .padding(16.dp)
        ) {
            Text(
                text = uiState.saludo,
                style = MaterialTheme.typography.headlineSmall,
                color = MarronTexto
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Racha: ${uiState.racha} días",
                style = MaterialTheme.typography.bodyLarge,
                color = DoradoPrimario
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onNavigateToLectura,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DoradoPrimario)
            ) {
                Text("📖 Lectura de Hoy")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onNavigateToJuegos,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeEsperanza)
            ) {
                Text("🎮 Juegos")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onNavigateToRetos,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Carmesi)
            ) {
                Text("🔥 Reto del Día")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onNavigateToProgreso,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DoradoOscuro)
            ) {
                Text("🏆 Mi Progreso")
            }
        }
    }
}