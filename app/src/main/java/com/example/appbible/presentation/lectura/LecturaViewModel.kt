package com.example.appbible.presentation.lectura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.ReadingProgressDao
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.data.repository.DailyReadingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class LecturaUiState(
    val referencia: String = "Genesis 1:1",
    val versiculo: String = "En el principio creo Dios los cielos y la tierra.",
    val reflexion: String = "En el principio de todo, Dios creo el universo con amor.",
    val version: String = "RVR1960",
    val rachaDias: Int = 0,
    val yaLeido: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class LecturaViewModel @Inject constructor(
    private val repository: DailyReadingRepository,
    private val progressDao: ReadingProgressDao,
    private val scoreDao: ScoreDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LecturaUiState())
    val uiState: StateFlow<LecturaUiState> = _uiState.asStateFlow()

    private val reflexiones = mapOf(
        "fe" to "La fe es la certeza de lo que se espera. Confia en que Dios tiene un plan perfecto para ti.",
        "amor" to "El amor verdadero es paciente. Hoy refleja sobre como mostrar amor sincero.",
        "esperanza" to "En medio de las pruebas, la esperanza nos mantiene firmes.",
        "sabiduria" to "La sabiduria comienza con el temor a Dios.",
        "oracion" to "La oracion es nuestra comunicacion directa con Dios.",
        "perdon" to "Perdonar es liberar tu corazon del peso del rencor.",
        "fortaleza" to "Dios es nuestra fortaleza en los momentos dificiles.",
        "gratitud" to "Da gracias en todo momento, porque todo proviene de Dios."
    )

    init {
        cargarLecturaDiaria()
    }

    private fun cargarLecturaDiaria() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val dayOfYear = repository.getCurrentDayOfYear()
                    val version = _uiState.value.version
                    
                    val verses = repository.getDailyReading(dayOfYear, version).first()
                    if (verses.isNotEmpty()) {
                        val verse = verses.first()
                        val theme = repository.getThematicWeekNumber()
                        val themeKey = DailyReadingRepository.THEMATIC_WEEKS.getOrNull(theme) ?: "fe"
                        val reflexion = reflexiones[themeKey] 
                            ?: "Dios te ama y tiene un plan perfecto para tu vida."
                        
                        val racha = repository.calculateStreak()
                        val currentDate = repository.getCurrentDateFormatted()
                        val progress = progressDao.getProgressByDate(currentDate).first()
                        
                        _uiState.value = LecturaUiState(
                            referencia = "${verse.book} ${verse.chapter}:${verse.verseNumber}",
                            versiculo = verse.text,
                            reflexion = reflexion,
                            version = version,
                            rachaDias = racha,
                            yaLeido = progress?.isCompleted == true,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun cambiarVersion(version: String) {
        _uiState.value = _uiState.value.copy(version = version)
        cargarLecturaDiaria()
    }

    fun marcarLeido() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val currentDate = repository.getCurrentDateFormatted()
                    val verses = repository.getDailyReading(
                        repository.getCurrentDayOfYear(), 
                        _uiState.value.version
                    ).first()
                    
                    if (verses.isNotEmpty()) {
                        repository.markAsRead(currentDate, verses.first().id, _uiState.value.version)
                        scoreDao.addXP(50)
                        scoreDao.incrementReadingsCompleted()
                        
                        _uiState.value = _uiState.value.copy(yaLeido = true)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(yaLeido = true)
            }
        }
    }

    fun siguienteLectura() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val nextDay = (repository.getCurrentDayOfYear() % 365) + 1
                    val version = _uiState.value.version
                    
                    val verses = repository.getDailyReading(nextDay, version).first()
                    if (verses.isNotEmpty()) {
                        val verse = verses.first()
                        _uiState.value = _uiState.value.copy(
                            referencia = "${verse.book} ${verse.chapter}:${verse.verseNumber}",
                            versiculo = verse.text,
                            yaLeido = false
                        )
                    }
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}