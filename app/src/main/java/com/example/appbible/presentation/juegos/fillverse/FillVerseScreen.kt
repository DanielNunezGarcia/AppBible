package com.example.appbible.presentation.juegos.fillverse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appbible.presentation.theme.*
import com.example.appbible.presentation.theme.RojoFeedback
import com.example.appbible.presentation.theme.VerdeFeedback

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
                    SelectorDificultadFillVerse(
                        onSeleccionar = { modo ->
                            viewModel.seleccionarDificultadYComenzar(modo)
                        },
                        onSalir = onFinish
                    )
                }
                uiState.juegoTerminado -> {
                    PantallaResultadosFillVerse(
                        score = uiState.score,
                        dificultad = uiState.dificultad,
                        onReiniciar = { viewModel.reiniciarJuego() },
                        onSalir = onFinish
                    )
                }
                else -> {
                    PantallaJuegoFillVerse(
                        uiState = uiState,
                        onSeleccionarOpcion = { viewModel.seleccionarOpcion(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectorDificultadFillVerse(
    onSeleccionar: (FillVerseModo) -> Unit,
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
            text = "Completa el Versículo",
            style = MaterialTheme.typography.headlineMedium,
            color = MarronTexto,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "50 versículos bíblicos",
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
                        text = "50 pts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MarronClaro
                    )
                }
                Button(
                    onClick = { onSeleccionar(FillVerseModo.FACIL) },
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
                    onClick = { onSeleccionar(FillVerseModo.MEDIO) },
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
                    onClick = { onSeleccionar(FillVerseModo.DIFICIL) },
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
                        onClick = { onSeleccionar(FillVerseModo.ALEATORIO) },
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
private fun PantallaJuegoFillVerse(
    uiState: FillVerseUiState,
    onSeleccionarOpcion: (String) -> Unit
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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
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
            val esSeleccionada = opcion == uiState.respuestaUsuario
            val esCorrecta = opcion == uiState.respuestaCorrectaTexto
            
            val colorFondo = when {
                uiState.mostrarFeedback && esCorrecta -> VerdeFeedback
                uiState.mostrarFeedback && esSeleccionada && !uiState.respuestaCorrecta -> RojoFeedback
                esSeleccionada && !uiState.mostrarFeedback -> MarronClaro.copy(alpha = 0.3f)
                else -> PergaminoClaro
            }

            val colorTexto = when {
                // Texto blanco sobre fondos oscuros
                uiState.mostrarFeedback && (esCorrecta || (esSeleccionada && !uiState.respuestaCorrecta)) -> Blanco
                else -> MarronTexto
            }

            Surface(
                onClick = {
                    if (!uiState.mostrarFeedback) {
                        onSeleccionarOpcion(opcion)
                    }
                },
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
                    textAlign = TextAlign.Center
                )
            }
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
                        text = if (uiState.respuestaCorrecta) "✅ ¡Correcto!" else "❌ Incorrecto",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (uiState.respuestaCorrecta) VerdeFeedback else RojoFeedback,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = if (uiState.respuestaCorrecta) 
                            "¡Muy bien! Continúa así." 
                        else 
                            "Sigue intentando, ¡tú puedes!",
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
                                text = "Respuesta correcta: ${uiState.respuestaCorrectaTexto}",
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
    }
}

@Composable
private fun PantallaResultadosFillVerse(
    score: Int,
    dificultad: FillVerseDificultad,
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
