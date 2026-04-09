package com.example.appbible.presentation.juegos.trivia

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appbible.game.model.TriviaQuestion
import com.example.appbible.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: TriviaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📝 Trivia Bíblica", fontWeight = FontWeight.Bold) },
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
                preguntasCorrectas = uiState.respuestasMarcadas.count { (indice, respuesta) ->
                    preguntas.getOrNull(indice)?.correctIndex == respuesta
                },
                totalPreguntas = uiState.totalPreguntas,
                onReiniciar = { viewModel.reiniciarJuego() },
                onSalir = onFinish,
                modifier = Modifier.padding(padding)
            )
        } else if (uiState.preguntaActual != null) {
            PantallaPregunta(
                pregunta = uiState.preguntaActual!!,
                indicePregunta = uiState.indicePregunta,
                totalPreguntas = uiState.totalPreguntas,
                vidas = uiState.vidas,
                tiempoRestante = uiState.tiempoRestante,
                mostrarFeedback = uiState.mostrarFeedback,
                respuestaCorrecta = uiState.respuestaCorrecta,
                respuestasMarcadas = uiState.respuestasMarcadas,
                onSeleccionarRespuesta = { viewModel.seleccionarRespuesta(it) },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

private var preguntas = listOf<TriviaQuestion>()

@Composable
private fun PantallaPregunta(
    pregunta: TriviaQuestion,
    indicePregunta: Int,
    totalPreguntas: Int,
    vidas: Int,
    tiempoRestante: Int,
    mostrarFeedback: Boolean,
    respuestaCorrecta: Boolean,
    respuestasMarcadas: Map<Int, Int>,
    onSeleccionarRespuesta: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    preguntas = listOf(pregunta)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    repeat(vidas) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Vida",
                            tint = Carmesi,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    repeat(3 - vidas) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Vida perdida",
                            tint = MarronClaro,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Text(
                    text = "⏱️ ${tiempoRestante}s",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (tiempoRestante <= 10) Carmesi else MarronTexto
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = { tiempoRestante / 25f },
                modifier = Modifier.fillMaxWidth(),
                color = if (tiempoRestante <= 10) Carmesi else DoradoPrimario,
                trackColor = PergaminoClaro
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Pregunta ${indicePregunta + 1} de $totalPreguntas",
                style = MaterialTheme.typography.bodyMedium,
                color = MarronClaro
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
            ) {
                Text(
                    text = pregunta.question,
                    style = MaterialTheme.typography.titleLarge,
                    color = MarronTexto,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            pregunta.options.forEachIndexed { indice, opcion ->
                val colorFondo = when {
                    mostrarFeedback && indice == pregunta.correctIndex -> VerdeClaro.copy(alpha = 0.3f)
                    mostrarFeedback && respuestasMarcadas[indicePregunta] == indice && !respuestaCorrecta -> Carmesi.copy(alpha = 0.3f)
                    else -> PergaminoClaro
                }
                
                val colorBorde = when {
                    mostrarFeedback && indice == pregunta.correctIndex -> VerdeEsperanza
                    mostrarFeedback && respuestasMarcadas[indicePregunta] == indice && !respuestaCorrecta -> Carmesi
                    else -> MarronClaro
                }
                
                OutlinedButton(
                    onClick = { onSeleccionarRespuesta(indice) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    enabled = !mostrarFeedback,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colorFondo
                    )
                ) {
                    Text(
                        text = opcion,
                        color = MarronTexto,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
            
            if (mostrarFeedback) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (respuestaCorrecta) VerdeEsperanza.copy(alpha = 0.1f) else Carmesi.copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (respuestaCorrecta) "✅ ¡Correcto!" else "❌ Incorrecto",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (respuestaCorrecta) VerdeEsperanza else Carmesi
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = pregunta.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MarronTexto
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = pregunta.reference,
                            style = MaterialTheme.typography.bodySmall,
                            color = DoradoPrimario
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
    preguntasCorrectas: Int,
    totalPreguntas: Int,
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
            color = DoradoOscuro,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "$preguntasCorrectas de $totalPreguntas correctas",
            style = MaterialTheme.typography.titleLarge,
            color = MarronTexto
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