package com.example.appbible.presentation.juegos.trivia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
                    SelectorDificultad(
                        onSeleccionar = { modo ->
                            viewModel.seleccionarDificultadYComenzar(modo)
                        },
                        onSalir = onFinish
                    )
                }
                uiState.juegoTerminado -> {
                    PantallaResultados(
                        score = uiState.score,
                        preguntasCorrectas = uiState.respuestasMarcadas.count { (_, respuesta) ->
                            uiState.preguntaActual?.correctIndex == respuesta
                        },
                        totalPreguntas = uiState.totalPreguntas,
                        dificultad = uiState.dificultadActual,
                        onReiniciar = { viewModel.reiniciarJuego() },
                        onSalir = onFinish
                    )
                }
                uiState.preguntaActual != null -> {
                    PantallaPregunta(
                        pregunta = uiState.preguntaActual!!,
                        indicePregunta = uiState.indicePregunta,
                        totalPreguntas = uiState.totalPreguntas,
                        vidas = uiState.vidas,
                        tiempoRestante = uiState.tiempoRestante,
                        tiempoTotal = uiState.dificultadActual.tiempo,
                        mostrarFeedback = uiState.mostrarFeedback,
                        respuestaCorrecta = uiState.respuestaCorrecta,
                        respuestasMarcadas = uiState.respuestasMarcadas,
                        onSeleccionarRespuesta = { viewModel.seleccionarRespuesta(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectorDificultad(
    onSeleccionar: (ModoJuego) -> Unit,
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
            text = "Trivia Bíblica",
            style = MaterialTheme.typography.headlineMedium,
            color = MarronTexto,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "100 preguntas sobre la Biblia",
            style = MaterialTheme.typography.bodyMedium,
            color = MarronClaro
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
                        text = "30s • 50 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(ModoJuego.FACIL) },
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
                        text = "20s • 100 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(ModoJuego.MEDIO) },
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
                        text = "15s • 150 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(ModoJuego.DIFICIL) },
                    modifier = Modifier.width(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Carmesi),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Jugar", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
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
                        onClick = { onSeleccionar(ModoJuego.ALEATORIO) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Jugar")
                    }
                }
            }
        }
    }
}

@Composable
private fun PantallaPregunta(
    pregunta: TriviaQuestion,
    indicePregunta: Int,
    totalPreguntas: Int,
    vidas: Int,
    tiempoRestante: Int,
    tiempoTotal: Int,
    mostrarFeedback: Boolean,
    respuestaCorrecta: Boolean,
    respuestasMarcadas: Map<Int, Int>,
    onSeleccionarRespuesta: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                color = if (tiempoRestante <= 5) Carmesi else MarronTexto
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LinearProgressIndicator(
            progress = { tiempoRestante.toFloat() / tiempoTotal },
            modifier = Modifier.fillMaxWidth(),
            color = if (tiempoRestante <= 5) Carmesi else DoradoPrimario,
            trackColor = PergaminoClaro
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
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
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        pregunta.options.forEachIndexed { indice, opcion ->
            val esSeleccionada = respuestasMarcadas[indicePregunta] == indice
            val esCorrecta = indice == pregunta.correctIndex
            
            val colorFondo = when {
                mostrarFeedback && esCorrecta -> VerdeFeedback
                mostrarFeedback && esSeleccionada && !respuestaCorrecta -> RojoFeedback
                esSeleccionada && !mostrarFeedback -> MarronClaro.copy(alpha = 0.3f)
                else -> PergaminoClaro
            }

            val colorTexto = when {
                // Texto blanco sobre fondos oscuros (verde/rojo)
                mostrarFeedback && (esCorrecta || (esSeleccionada && !respuestaCorrecta)) -> Blanco
                else -> MarronTexto
            }

            Surface(
                onClick = { if (!mostrarFeedback) onSeleccionarRespuesta(indice) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                color = colorFondo,
                contentColor = colorTexto
            ) {
                Text(
                    text = opcion,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    color = colorTexto,
                    textAlign = TextAlign.Start
                )
            }
        }
        
        if (mostrarFeedback) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (respuestaCorrecta) {
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
                        text = if (respuestaCorrecta) "✅ ¡Correcto!" else "❌ Incorrecto",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (respuestaCorrecta) VerdeFeedback else RojoFeedback,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = pregunta.explanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronTexto,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
                    ) {
                        Text(
                            text = pregunta.reference,
                            style = MaterialTheme.typography.bodyMedium,
                            color = DoradoPrimario,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
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
    dificultad: Dificultad,
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "$preguntasCorrectas de $totalPreguntas correctas",
            style = MaterialTheme.typography.titleLarge,
            color = MarronTexto
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
