package com.example.appbible.presentation.juegos.memorize

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appbible.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemorizeScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: MemorizeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📖 Memorización") },
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
        if (uiState.juegoTerminado) {
            PantallaResultados(
                score = uiState.score,
                onReiniciar = { viewModel.reiniciarJuego() },
                onSalir = onFinish,
                modifier = Modifier.padding(padding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tarjeta ${uiState.indiceActual + 1} de ${uiState.totalTarjetas}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MarronClaro
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nivel ${uiState.nivelActual} de ${uiState.totalNiveles}",
                    style = MaterialTheme.typography.titleMedium,
                    color = DoradoPrimario
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { uiState.nivelActual.toFloat() / uiState.totalNiveles },
                    modifier = Modifier.fillMaxWidth(),
                    color = DoradoPrimario,
                    trackColor = PergaminoClaro
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.referencia,
                            style = MaterialTheme.typography.titleMedium,
                            color = Carmesi
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = construirVersiculoOculto(uiState.versiculoActual, uiState.palabrasReveladas),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MarronTexto,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (uiState.palabrasReveladas.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.1f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Pistas reveladas:",
                                style = MaterialTheme.typography.bodySmall,
                                color = VerdeEsperanza
                            )
                            Text(
                                text = uiState.palabrasReveladas.joinToString(", "),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MarronTexto
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = uiState.respuestaUsuario,
                    onValueChange = { viewModel.setRespuesta(it) },
                    label = { Text("Escribe el versículo completo") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.mostrarFeedback,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DoradoPrimario,
                        unfocusedBorderColor = MarronClaro
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.nivelActual < uiState.totalNiveles) {
                    OutlinedButton(
                        onClick = { viewModel.revelarSiguienteNivel() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("💡 Revelar más pistas")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = { viewModel.verificarRespuesta() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.mostrarFeedback && uiState.respuestaUsuario.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = DoradoPrimario)
                ) {
                    Text("✅ Verificar")
                }

                if (uiState.mostrarFeedback) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (uiState.respuestaCorrecta) VerdeEsperanza.copy(alpha = 0.1f) else Carmesi.copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = if (uiState.respuestaCorrecta) "✅ ¡Excelente! ¡Memorizado!" else "❌ Intenta de nuevo",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (uiState.respuestaCorrecta) VerdeEsperanza else Carmesi,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Puntos: ${uiState.score}",
                    style = MaterialTheme.typography.titleMedium,
                    color = DoradoPrimario
                )
            }
        }
    }
}

private fun construirVersiculoOculto(texto: String, pistas: List<String>): String {
    if (pistas.isEmpty()) {
        return texto.map { if (it.isLetter()) '_' else it }.joinToString("")
    }
    
    val palabras = texto.split(" ")
    val pistasLower = pistas.map { it.lowercase() }
    
    return palabras.map { palabra ->
        val palabraLimpia = palabra.lowercase().replace(Regex("[^a-záéíóúüñ]"), "")
        if (pistasLower.any { pista -> 
            palabraLimpia.contains(pista) || pista.contains(palabraLimpia)
        }) {
            palabra
        } else {
            "_".repeat(palabra.length)
        }
    }.joinToString(" ")
}

@Composable
private fun PantallaResultados(
    score: Int,
    onReiniciar: () -> Unit,
    onSalir: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎉 ¡Juego Terminado!",
            style = MaterialTheme.typography.headlineLarge,
            color = DoradoPrimario
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Puntuación",
            style = MaterialTheme.typography.titleMedium,
            color = MarronClaro
        )

        Text(
            text = "$score",
            style = MaterialTheme.typography.displayLarge,
            color = DoradoOscuro
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReiniciar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DoradoPrimario)
        ) {
            Text("🔄 Jugar de nuevo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onSalir,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salir")
        }
    }
}