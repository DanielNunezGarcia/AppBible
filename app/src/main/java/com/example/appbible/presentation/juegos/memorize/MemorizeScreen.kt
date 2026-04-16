package com.example.appbible.presentation.juegos.memorize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appbible.presentation.theme.Blanco
import com.example.appbible.presentation.theme.Carmesi
import com.example.appbible.presentation.theme.DoradoOscuro
import com.example.appbible.presentation.theme.DoradoPrimario
import com.example.appbible.presentation.theme.MarronClaro
import com.example.appbible.presentation.theme.MarronTexto
import com.example.appbible.presentation.theme.PergaminoClaro
import com.example.appbible.presentation.theme.PergaminoFondo
import com.example.appbible.presentation.theme.RojoFeedback
import com.example.appbible.presentation.theme.VerdeEsperanza
import com.example.appbible.presentation.theme.VerdeFeedback

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
        Box(modifier = Modifier.padding(padding)) {
            when {
                uiState.mostrarSelectorDificultad -> {
                    SelectorDificultadMemorize(
                        onSeleccionar = viewModel::seleccionarDificultadYComenzar,
                        onSalir = onFinish
                    )
                }

                uiState.juegoTerminado -> {
                    PantallaResultadosMemorize(
                        score = uiState.score,
                        dificultad = uiState.dificultad,
                        onReiniciar = viewModel::reiniciarJuego,
                        onSalir = onFinish
                    )
                }

                else -> {
                    PantallaJuegoMemorize(
                        uiState = uiState,
                        onSetRespuesta = viewModel::setRespuesta,
                        onRevelarNivel = viewModel::revelarSiguienteNivel,
                        onVerificar = viewModel::verificarRespuesta
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectorDificultadMemorize(
    onSeleccionar: (MemorizeModo) -> Unit,
    onSalir: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Memorización",
            style = MaterialTheme.typography.headlineMedium,
            color = MarronTexto,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Aprende los versículos de memoria",
            style = MaterialTheme.typography.bodyMedium,
            color = MarronClaro,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Selecciona la dificultad",
            style = MaterialTheme.typography.titleMedium,
            color = MarronTexto
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🌱 Fácil",
                        style = MaterialTheme.typography.titleSmall,
                        color = VerdeEsperanza,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "50 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(MemorizeModo.FACIL) },
                    modifier = Modifier.width(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeEsperanza),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Jugar", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DoradoPrimario.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "⭐ Normal",
                        style = MaterialTheme.typography.titleSmall,
                        color = DoradoPrimario,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "100 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(MemorizeModo.MEDIO) },
                    modifier = Modifier.width(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DoradoPrimario),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Jugar", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Carmesi.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🔥 Difícil",
                        style = MaterialTheme.typography.titleSmall,
                        color = Carmesi,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "150 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(MemorizeModo.DIFICIL) },
                    modifier = Modifier.width(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Carmesi),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Jugar", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎲 Aleatorio",
                    style = MaterialTheme.typography.titleSmall,
                    color = MarronTexto,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Mezcla todo",
                    style = MaterialTheme.typography.bodySmall,
                    color = MarronClaro
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { onSeleccionar(MemorizeModo.ALEATORIO) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Jugar")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DificultadCardMemorize(
    titulo: String,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text("Jugar")
            }
        }
    }
}

@Composable
private fun PantallaJuegoMemorize(
    uiState: MemorizeUiState,
    onSetRespuesta: (String) -> Unit,
    onRevelarNivel: () -> Unit,
    onVerificar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.referencia,
                    style = MaterialTheme.typography.titleMedium,
                    color = Carmesi,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pistas reveladas:",
                        style = MaterialTheme.typography.bodySmall,
                        color = VerdeEsperanza,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = uiState.palabrasReveladas.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronTexto,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.respuestaUsuario,
            onValueChange = onSetRespuesta,
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
                onClick = onRevelarNivel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("💡 Revelar más pistas")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = onVerificar,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.mostrarFeedback && uiState.respuestaUsuario.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.mostrarFeedback) {
                    if (uiState.respuestaCorrecta) VerdeFeedback else RojoFeedback
                } else {
                    DoradoPrimario
                }
            )
        ) {
            Text(
                text = if (uiState.mostrarFeedback) {
                    if (uiState.respuestaCorrecta) "✅ ¡Correcto!" else "❌ Incorrecto"
                } else {
                    "✅ Verificar"
                },
                color = Blanco
            )
        }

        if (uiState.mostrarFeedback) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.respuestaCorrecta) {
                        VerdeFeedback.copy(alpha = 0.15f)
                    } else {
                        RojoFeedback.copy(alpha = 0.15f)
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (uiState.respuestaCorrecta) "✅ ¡Excelente! ¡Memorizado!" else "❌ Intenta de nuevo",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (uiState.respuestaCorrecta) VerdeFeedback else RojoFeedback,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (uiState.respuestaCorrecta) {
                            "¡Muy bien! Continúa así."
                        } else {
                            "Sigue intentando, ¡tú puedes!"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronTexto,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.referencia,
                                style = MaterialTheme.typography.bodyMedium,
                                color = DoradoPrimario,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.versiculoActual,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MarronTexto,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Puntos: ${uiState.score}",
            style = MaterialTheme.typography.titleMedium,
            color = DoradoPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PantallaResultadosMemorize(
    score: Int,
    dificultad: MemorizeDificultad,
    onReiniciar: () -> Unit,
    onSalir: () -> Unit
) {
    Column(
        modifier = Modifier
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
            color = DoradoOscuro,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Dificultad: ${dificultad.displayName}",
            style = MaterialTheme.typography.bodyMedium,
            color = MarronClaro
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
            }
        ) {
            palabra
        } else {
            "_".repeat(palabra.length)
        }
    }.joinToString(" ")
}
