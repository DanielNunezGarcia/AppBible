package com.example.appbible.presentation.progreso

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.AchievementDao
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.data.local.entity.AchievementEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProgresoUiState(
    val nivel: Int = 1,
    val xpActual: Int = 0,
    val xpSiguienteNivel: Int = 1000,
    val puntosTotales: Int = 0,
    val rachaDias: Int = 0,
    val lecturasCompletadas: Int = 0,
    val versiculosMemorizados: Int = 0,
    val juegosJugados: Int = 0,
    val logrosDesbloqueados: List<AchievementEntity> = emptyList(),
    val totalLogros: Int = 24,
    val mejorTrivia: Int = 0,
    val mejorFillVerse: Int = 0,
    val mejorMemoriza: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class ProgresoViewModel @Inject constructor(
    private val scoreDao: ScoreDao,
    private val achievementDao: AchievementDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgresoUiState())
    val uiState: StateFlow<ProgresoUiState> = _uiState.asStateFlow()

    init {
        cargarProgreso()
    }

    private fun cargarProgreso() {
        viewModelScope.launch {
            try {
                val score = scoreDao.getUserScore().first()
                val achievements = achievementDao.getUnlockedAchievements().first()
                val totalLogros = achievementDao.getAllAchievements().first().size

                val nivel = score?.level ?: 1
                val xpActual = score?.totalXP ?: 0
                val xpSiguienteNivel = nivel * 1000

                _uiState.value = ProgresoUiState(
                    nivel = nivel,
                    xpActual = xpActual,
                    xpSiguienteNivel = xpSiguienteNivel,
                    puntosTotales = score?.totalPoints ?: 0,
                    rachaDias = score?.streakDays ?: 0,
                    lecturasCompletadas = score?.readingsCompleted ?: 0,
                    versiculosMemorizados = score?.versesMemorized ?: 0,
                    juegosJugados = score?.gamesPlayed ?: 0,
                    logrosDesbloqueados = achievements,
                    totalLogros = totalLogros,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = ProgresoUiState(isLoading = false)
            }
        }
    }
}