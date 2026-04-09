package com.example.appbible.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.data.local.entity.ScoreEntity
import com.example.appbible.data.repository.DailyReadingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject

data class HomeUiState(
    val puntos: Int = 0,
    val saludo: String = "Hola",
    val racha: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scoreDao: ScoreDao,
    private val repository: DailyReadingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        val saludo = getSaludo()
        
        _uiState.value = HomeUiState(
            puntos = 0,
            saludo = saludo,
            racha = 0,
            isLoading = false
        )
        
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    scoreDao.initializeScore(
                        ScoreEntity(
                            totalPoints = 0,
                            totalXP = 0,
                            gamesPlayed = 0,
                            gamesWon = 0,
                            streakDays = 0,
                            bestStreakDays = 0,
                            versesMemorized = 0,
                            readingsCompleted = 0,
                            level = 1,
                            lastActiveDate = ""
                        )
                    )
                    
                    val score = scoreDao.getUserScore()
                    score.collect { scoreData ->
                        val puntos = scoreData?.totalPoints ?: 0
                        val racha = try {
                            repository.calculateStreak()
                        } catch (e: Exception) {
                            0
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            puntos = puntos,
                            racha = racha,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun getSaludo(): String {
        val hour = LocalTime.now().hour
        return when {
            hour < 12 -> "Buenos dias"
            hour < 18 -> "Buenas tardes"
            else -> "Buenas noches"
        }
    }
}