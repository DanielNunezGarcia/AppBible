package com.example.appbible.presentation.lectura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbible.data.local.dao.ReadingProgressDao
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.data.repository.DailyReadingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LecturaUiState(
    val referencia: String = "",
    val versiculo: String = "",
    val reflexion: String = "",
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
        "fe" to "La fe es la certeza de lo que se espera y la convicción de lo que no se ve. Confía en que Dios tiene un plan perfecto para ti.",
        "amor" to "El amor verdadero es paciente, es bondadoso. Hoy reflexiona sobre cómo puedes mostrar amor sincero a quienes te rodean.",
        "esperanza" to "En medio de las pruebas, la esperanza nos mantiene firmes. Recuerda que Dios es nuestra luz y salvación.",
        "sabiduria" to "La sabiduría comienza con el temor a Dios. Pide hoy discernimiento para tomar decisiones sabias.",
        "oracion" to "La oración es nuestra comunicación directa con Dios. No dejes de orar en todo momento.",
        "perdon" to "Perdonar es liberar tu corazón del peso del rencor. Hoy elige perdonar como Dios te ha perdonado.",
        "fortaleza" to "Dios es nuestra fortaleza en los momentos difíciles. No temas, Él está contigo.",
        "gratitud" to "Da gracias en todo momento, porque todo proviene de Dios. Cultiva un corazón agradecido."
    )

    init {
        cargarLecturaDiaria()
    }

    private fun cargarLecturaDiaria() {
        viewModelScope.launch {
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
            }
        }
    }

    fun cambiarVersion(version: String) {
        _uiState.value = _uiState.value.copy(version = version)
        cargarLecturaDiaria()
    }

    fun marcarLeido() {
        viewModelScope.launch {
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
    }

    fun siguienteLectura() {
        viewModelScope.launch {
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
    }
}