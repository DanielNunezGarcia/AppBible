package com.example.appbible.presentation.progreso

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
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
fun ProgresoScreen(
    onBack: () -> Unit,
    viewModel: ProgresoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🏆 Mi Progreso", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DoradoPrimario)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nivel ${uiState.nivel}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Blanco,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${uiState.xpActual} / ${uiState.xpSiguienteNivel} XP",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Blanco.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { uiState.xpActual.toFloat() / uiState.xpSiguienteNivel },
                        modifier = Modifier.fillMaxWidth(),
                        color = DoradoClaro,
                        trackColor = Blanco.copy(alpha = 0.3f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Estadísticas",
                style = MaterialTheme.typography.titleLarge,
                color = MarronTexto,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    icon = Icons.Default.LocalFireDepartment,
                    label = "Racha",
                    value = "${uiState.rachaDias} días",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Default.MenuBook,
                    label = "Lecturas",
                    value = "${uiState.lecturasCompletadas}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    icon = Icons.Default.Star,
                    label = "Puntos",
                    value = "${uiState.puntosTotales}",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Default.EmojiEvents,
                    label = "Versículos",
                    value = "${uiState.versiculosMemorizados}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Logros",
                style = MaterialTheme.typography.titleLarge,
                color = MarronTexto,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${uiState.logrosDesbloqueados.size} de ${uiState.totalLogros} desbloqueados",
                style = MaterialTheme.typography.bodyMedium,
                color = MarronClaro
            )

            Spacer(modifier = Modifier.height(8.dp))

            uiState.logrosDesbloqueados.forEach { logro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🏆",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = logro.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MarronTexto,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = logro.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MarronClaro
                            )
                        }
                    }
                }
            }

            if (uiState.logrosDesbloqueados.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MarronClaro.copy(alpha = 0.1f))
                ) {
                    Text(
                        text = "¡Completa lecturas y juegos para desbloquear logros!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronClaro,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Mejores Puntuaciones",
                style = MaterialTheme.typography.titleLarge,
                color = MarronTexto,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ScoreRow("Trivia", uiState.mejorTrivia)
                    ScoreRow("Completa el Versículo", uiState.mejorFillVerse)
                    ScoreRow("Memorización", uiState.mejorMemoriza)
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = DoradoPrimario,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MarronTexto,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MarronClaro
            )
        }
    }
}

@Composable
private fun ScoreRow(juego: String, puntuacion: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = juego,
            style = MaterialTheme.typography.bodyMedium,
            color = MarronTexto
        )
        Text(
            text = "$puntuacion pts",
            style = MaterialTheme.typography.bodyMedium,
            color = DoradoPrimario,
            fontWeight = FontWeight.Bold
        )
    }
}