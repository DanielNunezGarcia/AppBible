package com.example.appbible.presentation.juegos.fillverse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.game.engine.FillVerseEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FillVerseUiState(
    val versiculoActual: String = "",
    val referencia: String = "",
    val respuestaUsuario: String = "",
    val opciones: List<String> = emptyList(),
    val score: Int = 0,
    val vidas: Int = 3,
    val indiceActual: Int = 0,
    val totalVersiculos: Int = 5,
    val mostrarFeedback: Boolean = false,
    val respuestaCorrecta: Boolean = false,
    val juegoTerminado: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class FillVerseViewModel @Inject constructor(
    private val fillVerseEngine: FillVerseEngine,
    private val scoreDao: ScoreDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(FillVerseUiState())
    val uiState: StateFlow<FillVerseUiState> = _uiState.asStateFlow()
    
    private var stateCollectionJob: Job? = null

    init {
        iniciarJuego()
    }

    private fun iniciarJuego() {
        fillVerseEngine.loadVerses("facil")
        
        // Cancelar colección anterior para evitar memory leaks
        stateCollectionJob?.cancel()
        stateCollectionJob = fillVerseEngine.currentState
            .onEach { state ->
                // Si el juego terminó y no es loading, guardar puntuación
                if (state.isGameOver && !_uiState.value.isLoading) {
                    guardarPuntuacion()
                }
                
                _uiState.value = FillVerseUiState(
                    versiculoActual = state.currentVerse?.fullText ?: "",
                    referencia = state.currentVerse?.reference ?: "",
                    opciones = state.currentVerse?.options ?: emptyList(),
                    score = state.score,
                    vidas = state.lives,
                    indiceActual = state.currentIndex,
                    totalVersiculos = state.verses.size,
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
            val resultado = fillVerseEngine.finishGame()
            scoreDao.incrementGamesPlayed()
            if (_uiState.value.vidas > 0) {
                scoreDao.incrementGamesWon()
            }
            scoreDao.addPoints(resultado.pointsEarned)
            scoreDao.addXP(resultado.xpEarned)
        }
    }

    fun setRespuesta(respuesta: String) {
        fillVerseEngine.setUserAnswer(respuesta)
        _uiState.value = _uiState.value.copy(respuestaUsuario = respuesta)
    }

    fun verificarRespuesta() {
        val esCorrecta = fillVerseEngine.checkAnswer()
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            fillVerseEngine.nextVerse()
            _uiState.value = _uiState.value.copy(respuestaUsuario = "")
        }
    }

    fun seleccionarOpcion(opcion: String) {
        setRespuesta(opcion)
        verificarRespuesta()
    }

    fun reiniciarJuego() {
        iniciarJuego()
    }
}