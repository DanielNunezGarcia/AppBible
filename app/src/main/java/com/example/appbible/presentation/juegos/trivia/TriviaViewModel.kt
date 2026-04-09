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
    val isLoading: Boolean = true
)

@HiltViewModel
class TriviaViewModel @Inject constructor(
    private val scoreDao: ScoreDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(TriviaUiState())
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()

    private var preguntas: List<TriviaQuestion> = emptyList()
    private var timerJob: Job? = null

    init {
        iniciarJuego()
    }

    private fun iniciarJuego() {
        val todasLasPreguntas = getTriviaQuestions()
        preguntas = todasLasPreguntas.shuffled().take(10)
        
        _uiState.value = TriviaUiState(
            preguntaActual = preguntas.firstOrNull(),
            indicePregunta = 0,
            totalPreguntas = preguntas.size,
            vidas = 3,
            score = 0,
            tiempoRestante = 25,
            respuestasMarcadas = emptyMap(),
            juegoTerminado = false,
            isLoading = false
        )
        
        iniciarTimer()
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
        val nuevoScore = if (correcta) _uiState.value.score + 100 else _uiState.value.score
        
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
            delay(2000)
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
            tiempoRestante = 25,
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

    fun reiniciarJuego() {
        iniciarJuego()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}