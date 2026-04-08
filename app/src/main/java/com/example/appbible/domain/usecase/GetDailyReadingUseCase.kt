package com.example.appbible.domain.usecase

import com.example.appbible.data.repository.DailyReadingRepository
import com.example.appbible.domain.model.DailyReading
import com.example.appbible.domain.model.Verse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetDailyReadingUseCase(
    private val repository: DailyReadingRepository
) {

    operator fun invoke(version: String = "RVR1960"): Flow<DailyReading> {
        val dayOfYear = getCurrentDayOfYear()
        val isWeekend = isWeekend()
        
        return if (isWeekend) {
            // Fin de semana: lectura temática
            val weekNumber = getThematicWeekNumber()
            val theme = DailyReadingRepository.THEMATIC_WEEKS[weekNumber]
            repository.getThematicReading(theme, version)
                .map { entities ->
                    DailyReading(
                        verses = entities.map { it.toDomainModel() },
                        date = getCurrentDateFormatted(),
                        dayNumber = dayOfYear,
                        isThematic = true,
                        isCompleted = false,
                        streak = repository.calculateStreak(),
                        reflection = getThematicReflection(theme)
                    )
                }
        } else {
            // Lunes a Viernes: lectura cronológica
            repository.getDailyReading(dayOfYear, version)
                .map { entities ->
                    DailyReading(
                        verses = entities.map { it.toDomainModel() },
                        date = getCurrentDateFormatted(),
                        dayNumber = dayOfYear,
                        isThematic = false,
                        isCompleted = false,
                        streak = repository.calculateStreak(),
                        reflection = getCronologicalReflection(dayOfYear)
                    )
                }
        }
    }

    private fun getCurrentDayOfYear(): Int {
        return LocalDate.now().dayOfYear
    }

    private fun getCurrentDateFormatted(): String {
        return LocalDate.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
    }

    private fun isWeekend(): Boolean {
        val dayOfWeek = LocalDate.now().dayOfWeek.value
        return dayOfWeek == 6 || dayOfWeek == 7
    }

    private fun getThematicWeekNumber(): Int {
        val dayOfYear = getCurrentDayOfYear()
        return ((dayOfYear - 1) / 7) % 8
    }

    private fun getThematicReflection(theme: String): String {
        return when (theme) {
            "fe" -> "La fe es el fundamento de nuestra relación con Dios. Medita en cómo la fe ha transformado vidas a lo largo de la Biblia."
            "amor" -> "El amor de Dios es incondicional y eterno. Reflexiona en cómo Su amor se manifiesta en tu vida diaria."
            "esperanza" -> "La esperanza en Dios nunca defrauda. Él tiene planes de bien para ti, no de mal."
            "sabiduría" -> "La sabiduría que viene de Dios es pura, pacífica y amable. Pídele sabiduría hoy."
            "oración" -> "La oración es el aliento del alma. Dios escucha cada clamor de tu corazón."
            "perdón" -> "El perdón libera el alma. Así como Dios te perdonó, perdona a otros."
            "fortaleza" -> "Dios es tu fortaleza y tu refugio. En Él encuentras fuerzas para seguir adelante."
            "gratitud" -> "La gratitud abre las puertas de la bendición. Da gracias en todo, porque esta es la voluntad de Dios."
            else -> "La Palabra de Dios es lámpara a tus pies. Medita en ella día y noche."
        }
    }

    private fun getCronologicalReflection(day: Int): String {
        return when {
            day <= 25 -> "Estás comenzando tu viaje por la Biblia. Génesis nos muestra el origen de todo: la creación, la caída, y las promesas de Dios a los patriarcas."
            day <= 50 -> "El Éxodo nos enseña sobre la liberación de Dios. Él te libera de la esclavitud del pecado."
            day <= 75 -> "Los libros históricos muestran la fidelidad de Dios a través de las generaciones."
            day <= 100 -> "Los libros poéticos nos invitan a alabar a Dios en todo tiempo."
            day <= 150 -> "Los profetas anunciaron la venida del Mesías. Jesús es el cumplimiento de todas las promesas."
            else -> "El Nuevo Testamento revela el plan de salvación de Dios. Vive en Cristo cada día."
        }
    }

    private fun com.example.appbible.data.local.entity.VerseEntity.toDomainModel(): Verse {
        return Verse(
            id = this.id,
            book = this.book,
            chapter = this.chapter,
            verseNumber = this.verseNumber,
            text = this.text,
            reference = this.reference,
            version = this.version,
            theme = this.theme
        )
    }
}
