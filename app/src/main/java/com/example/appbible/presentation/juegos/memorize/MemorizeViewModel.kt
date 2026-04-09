package com.example.appbible.presentation.juegos.memorize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.game.engine.MemorizeEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MemorizeUiState(
    val versiculoActual: String = "",
    val referencia: String = "",
    val nivelActual: Int = 1,
    val totalNiveles: Int = 5,
    val palabrasReveladas: List<String> = emptyList(),
    val respuestaUsuario: String = "",
    val score: Int = 0,
    val indiceActual: Int = 0,
    val totalTarjetas: Int = 5,
    val mostrarFeedback: Boolean = false,
    val respuestaCorrecta: Boolean = false,
    val juegoTerminado: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class MemorizeViewModel @Inject constructor(
    private val memorizeEngine: MemorizeEngine,
    private val scoreDao: ScoreDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemorizeUiState())
    val uiState: StateFlow<MemorizeUiState> = _uiState.asStateFlow()
    
    private var stateCollectionJob: Job? = null

    init {
        iniciarJuego()
    }

    private fun iniciarJuego() {
        memorizeEngine.loadFlashCards("facil")
        
        // Cancelar colección anterior para evitar memory leaks
        stateCollectionJob?.cancel()
        stateCollectionJob = memorizeEngine.currentState
            .onEach { state ->
                // Si el juego terminó y no es loading, guardar puntuación
                if (state.isGameOver && !_uiState.value.isLoading) {
                    guardarPuntuacion()
                }
                
                _uiState.value = MemorizeUiState(
                    versiculoActual = state.currentCard?.verseText ?: "",
                    referencia = state.currentCard?.reference ?: "",
                    nivelActual = state.currentLevel,
                    totalNiveles = state.totalLevels,
                    palabrasReveladas = state.revealedWords,
                    respuestaUsuario = state.userAnswer,
                    score = state.score,
                    indiceActual = state.currentIndex,
                    totalTarjetas = state.flashCards.size,
                    mostrarFeedback = state.isAnswered,
                    respuestaCorrecta = state.isCorrect ?: false,
                    juegoTerminado = state.isGameOver,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
    
    private fun guardarPuntuacion() {
        viewModelScope.launch {
            val resultado = memorizeEngine.finishGame()
            scoreDao.incrementGamesPlayed()
            scoreDao.addPoints(resultado.pointsEarned)
            scoreDao.addXP(resultado.xpEarned)
            scoreDao.incrementVersesMemorized()
        }
    }

    fun setRespuesta(respuesta: String) {
        memorizeEngine.setUserAnswer(respuesta)
        _uiState.value = _uiState.value.copy(respuestaUsuario = respuesta)
    }

    fun revelarSiguienteNivel() {
        memorizeEngine.revealNextLevel()
    }

    fun verificarRespuesta() {
        val esCorrecta = memorizeEngine.checkAnswer()
        viewModelScope.launch {
            kotlinx.coroutines.delay(1500)
            memorizeEngine.nextCard()
            _uiState.value = _uiState.value.copy(respuestaUsuario = "")
        }
    }

    fun reiniciarJuego() {
        iniciarJuego()
    }
}