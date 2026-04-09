package com.example.appbible.presentation.juegos.fillverse

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
fun FillVerseScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: FillVerseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📝 Completa el Versículo") },
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
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        repeat(uiState.vidas) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Vida",
                                tint = Carmesi,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        repeat(3 - uiState.vidas) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Vida perdida",
                                tint = MarronClaro,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Text(
                        text = "Puntos: ${uiState.score}",
                        style = MaterialTheme.typography.titleMedium,
                        color = DoradoPrimario
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Versículo ${uiState.indiceActual + 1} de ${uiState.totalVersiculos}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MarronClaro
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = uiState.versiculoActual,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MarronTexto,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Selecciona la palabra correcta:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MarronClaro
                )

                Spacer(modifier = Modifier.height(8.dp))

                uiState.opciones.forEach { opcion ->
                    val colorFondo = when {
                        uiState.mostrarFeedback && opcion == uiState.respuestaUsuario && uiState.respuestaCorrecta -> VerdeClaro.copy(alpha = 0.3f)
                        uiState.mostrarFeedback && opcion == uiState.respuestaUsuario && !uiState.respuestaCorrecta -> Carmesi.copy(alpha = 0.3f)
                        else -> PergaminoClaro
                    }

                    OutlinedButton(
                        onClick = {
                            if (!uiState.mostrarFeedback) {
                                viewModel.seleccionarOpcion(opcion)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        enabled = !uiState.mostrarFeedback,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = colorFondo)
                    ) {
                        Text(
                            text = opcion,
                            color = MarronTexto
                        )
                    }
                }

                if (uiState.mostrarFeedback) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (uiState.respuestaCorrecta) VerdeEsperanza.copy(alpha = 0.1f) else Carmesi.copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = if (uiState.respuestaCorrecta) "✅ ¡Correcto!" else "❌ Intenta con otro",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (uiState.respuestaCorrecta) VerdeEsperanza else Carmesi,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
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