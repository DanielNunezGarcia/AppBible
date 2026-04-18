package com.example.appbible.presentation.juegos.trivia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.game.content.getTriviaQuestions
import com.example.appbible.game.model.TriviaQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Dificultad(val displayName: String, val tiempo: Int, val puntosBase: Int) {
    FACIL("Fácil", 15, 50),
    MEDIO("Normal", 20, 100),
    DIFICIL("Difícil", 30, 150)
}

enum class ModoJuego(val displayName: String) {
    FACIL("Fácil"),
    MEDIO("Medio"),
    DIFICIL("Difícil"),
    ALEATORIO("Aleatorio")
}

data class TriviaUiState(
    val preguntaActual: TriviaQuestion? = null,
    val indicePregunta: Int = 0,
    val totalPreguntas: Int = 10,
    val vidas: Int = 3,
    val score: Int = 0,
    val tiempoRestante: Int = 25,
    val respuestasMarcadas: Map<Int, Int> = emptyMap(),
    val juegoTerminado: Boolean = false,
    val mostrarFeedback: Boolean = false,
    val respuestaCorrecta: Boolean = false,
    val isLoading: Boolean = true,
    val dificultadActual: Dificultad = Dificultad.MEDIO,
    val mostrarSelectorDificultad: Boolean = true
)

@HiltViewModel
class TriviaViewModel @Inject constructor(
    private val scoreDao: ScoreDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(TriviaUiState())
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()

    private var preguntas: List<TriviaQuestion> = emptyList()
    private var timerJob: Job? = null

    fun seleccionarDificultadYComenzar(modo: ModoJuego) {
        val dificultad = when (modo) {
            ModoJuego.FACIL -> Dificultad.FACIL
            ModoJuego.MEDIO -> Dificultad.MEDIO
            ModoJuego.DIFICIL -> Dificultad.DIFICIL
            ModoJuego.ALEATORIO -> Dificultad.MEDIO
        }

        val todasLasPreguntas = getTriviaQuestions()
        preguntas = when (modo) {
            ModoJuego.FACIL -> todasLasPreguntas.filter { it.difficulty == "facil" }.shuffled().take(10)
            ModoJuego.MEDIO -> todasLasPreguntas.filter { it.difficulty == "medio" }.shuffled().take(10)
            ModoJuego.DIFICIL -> todasLasPreguntas.filter { it.difficulty == "dificil" }.shuffled().take(10)
            ModoJuego.ALEATORIO -> todasLasPreguntas.shuffled().take(10)
        }

        if (preguntas.isEmpty()) {
            preguntas = todasLasPreguntas.shuffled().take(10)
        }

        _uiState.value = TriviaUiState(
            preguntaActual = preguntas.firstOrNull(),
            indicePregunta = 0,
            totalPreguntas = preguntas.size,
            vidas = 3,
            score = 0,
            tiempoRestante = dificultad.tiempo,
            respuestasMarcadas = emptyMap(),
            juegoTerminado = false,
            isLoading = false,
            dificultadActual = dificultad,
            mostrarSelectorDificultad = false
        )

        iniciarTimer()
    }

    fun reiniciarJuego() {
        _uiState.value = _uiState.value.copy(mostrarSelectorDificultad = true)
    }

    private fun iniciarTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.tiempoRestante > 0 && !_uiState.value.juegoTerminado) {
                delay(1000)
                _uiState.value = _uiState.value.copy(tiempoRestante = _uiState.value.tiempoRestante - 1)
                
                if (_uiState.value.tiempoRestante == 0) {
                    responder(-1)
                }
            }
        }
    }

    fun seleccionarRespuesta(indice: Int) {
        if (_uiState.value.mostrarFeedback) return
        responder(indice)
    }

    private fun responder(indice: Int) {
        timerJob?.cancel()
        
        val pregunta = _uiState.value.preguntaActual ?: return
        val correcta = indice == pregunta.correctIndex
        val nuevaVidas = if (correcta) _uiState.value.vidas else _uiState.value.vidas - 1
        val nuevoScore = if (correcta) _uiState.value.score + _uiState.value.dificultadActual.puntosBase else _uiState.value.score
        
        val nuevasRespuestas = _uiState.value.respuestasMarcadas.toMutableMap()
        nuevasRespuestas[_uiState.value.indicePregunta] = indice
        
        _uiState.value = _uiState.value.copy(
            vidas = nuevaVidas,
            score = nuevoScore,
            respuestasMarcadas = nuevasRespuestas,
            mostrarFeedback = true,
            respuestaCorrecta = correcta
        )
        
        viewModelScope.launch {
            delay(6000)
            siguientePregunta()
        }
    }

    private fun siguientePregunta() {
        val siguienteIndice = _uiState.value.indicePregunta + 1
        
        if (siguienteIndice >= preguntas.size || _uiState.value.vidas <= 0) {
            terminarJuego()
            return
        }
        
        _uiState.value = _uiState.value.copy(
            preguntaActual = preguntas[siguienteIndice],
            indicePregunta = siguienteIndice,
            tiempoRestante = _uiState.value.dificultadActual.tiempo,
            mostrarFeedback = false
        )
        
        iniciarTimer()
    }

    private fun terminarJuego() {
        timerJob?.cancel()
        
        viewModelScope.launch {
            scoreDao.incrementGamesPlayed()
            if (_uiState.value.vidas > 0) {
                scoreDao.incrementGamesWon()
            }
            scoreDao.addPoints(_uiState.value.score)
            scoreDao.addXP(_uiState.value.score / 10)
        }
        
        _uiState.value = _uiState.value.copy(
            juegoTerminado = true,
            mostrarFeedback = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
