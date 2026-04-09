package com.example.appbible.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

data class HomeUiState(
    val puntos: Int = 0,
    val saludo: String = "¡Hola!",
    val racha: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {
                val saludo = getSaludo()
                
                _uiState.value = HomeUiState(
                    puntos = 0,
                    saludo = saludo,
                    racha = 0,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
                    puntos = 0,
                    saludo = "¡Hola!",
                    racha = 0,
                    isLoading = false
                )
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