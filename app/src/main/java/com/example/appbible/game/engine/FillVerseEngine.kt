package com.example.appbible.game.engine

import com.example.appbible.game.model.GameResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FillVerseEngine @Inject constructor() {

    private val _currentState = MutableStateFlow(FillVerseState())
    val currentState: StateFlow<FillVerseState> = _currentState.asStateFlow()

    private var allVerses: List<FillVerseData> = emptyList()
    private var currentVerses: List<FillVerseData> = emptyList()

    fun loadVerses(difficulty: String = "facil") {
        allVerses = getFillVerses()
        currentVerses = when (difficulty) {
            "dificil" -> allVerses.filter { it.missingWords >= 10 }
            "medio" -> allVerses.filter { it.missingWords in 5..9 }
            else -> allVerses.filter { it.missingWords <= 4 }
        }.shuffled().take(5)

        _currentState.value = FillVerseState(
            verses = currentVerses,
            currentIndex = 0,
            score = 0,
            correctAnswers = 0,
            wrongAnswers = 0,
            lives = 3,
            isGameOver = false,
            userAnswer = ""
        )
    }

    fun setUserAnswer(answer: String) {
        _currentState.value = _currentState.value.copy(userAnswer = answer)
    }

    fun checkAnswer(): Boolean {
        val state = _currentState.value
        val currentVerse = state.verses.getOrNull(state.currentIndex) ?: return false
        val isCorrect = state.userAnswer.trim()
            .equals(currentVerse.correctWords.joinToString(" "), ignoreCase = true)

        val newScore = if (isCorrect) {
            state.score + (100 * currentVerse.missingWords)
        } else {
            state.score
        }

        _currentState.value = state.copy(
            isAnswered = true,
            isCorrect = isCorrect,
            score = newScore,
            correctAnswers = if (isCorrect) state.correctAnswers + 1 else state.correctAnswers,
            wrongAnswers = if (!isCorrect) state.wrongAnswers + 1 else state.wrongAnswers,
            lives = if (!isCorrect) state.lives - 1 else state.lives
        )

        return isCorrect
    }

    fun nextVerse() {
        val state = _currentState.value
        if (state.currentIndex >= state.verses.size - 1) {
            _currentState.value = state.copy(isGameOver = true)
            return
        }

        _currentState.value = state.copy(
            currentIndex = state.currentIndex + 1,
            userAnswer = "",
            isAnswered = false,
            isCorrect = null
        )
    }

    fun finishGame(): GameResult {
        val state = _currentState.value
        return GameResult(
            gameType = "fillverse",
            score = state.score,
            totalQuestions = state.verses.size,
            correctAnswers = state.correctAnswers,
            wrongAnswers = state.wrongAnswers,
            xpEarned = state.score / 10 + (state.correctAnswers * 10),
            pointsEarned = state.score,
            durationSeconds = 0,
            newAchievements = emptyList()
        )
    }

    data class FillVerseData(
        val id: Int,
        val fullText: String,
        val reference: String,
        val missingWords: Int,
        val correctWords: List<String>,
        val options: List<String>
    )

    data class FillVerseState(
        val verses: List<FillVerseData> = emptyList(),
        val currentIndex: Int = 0,
        val score: Int = 0,
        val correctAnswers: Int = 0,
        val wrongAnswers: Int = 0,
        val lives: Int = 3,
        val isGameOver: Boolean = false,
        val userAnswer: String = "",
        val isAnswered: Boolean = false,
        val isCorrect: Boolean? = null
    ) {
        val currentVerse: FillVerseData? get() = verses.getOrNull(currentIndex)
    }

    companion object {
        private fun getFillVerses(): List<FillVerseData> {
            return listOf(
                FillVerseData(1, "En el ___ creó Dios los cielos y la tierra.", "Génesis 1:1", 2, listOf("principio"), listOf("principio", "tiempo", "inicio", "origen")),
                FillVerseData(2, "Jehová es mi pastor; ___ me faltará.", "Salmo 23:1", 2, listOf("no"), listOf("no", "jamás", "nunca", "solamente")),
                FillVerseData(3, "De tanto amó Dios al mundo, que ___ a su Hijo unigénito.", "Juan 3:16", 1, listOf("dio"), listOf("dio", "entregó", "envió", "mandó")),
                FillVerseData(4, "Todo aquel que crere en él no se perderá, sino que ___ vida eterna.", "Juan 3:16", 1, listOf("tendrá"), listOf("tendrá", "recibirá", "obtendrás", "tuvieras")),
                FillVerseData(5, "Jesús le dijo: Yo soy el camino, y la verdad, y la vida; nadie viene al Padre sino ___ mi.", "Juan 14:6", 2, listOf("por"), listOf("por", "mediante", "a través", "según")),
                FillVerseData(6, "El señor es mi luz y mi salvación; ___ temeré.", "Salmo 27:1", 1, listOf("a quién"), listOf("a quién", "nada", "nunca", "a nadie")),
                FillVerseData(7, "Contigo está la ___ de tu palabra.", "Salmo 119:41", 1, listOf("misericordia"), listOf("misericordia", "gracia", "bondad", "grandeza")),
                FillVerseData(8, "El que ___ en mí, aunque muera, vivirá.", "Juan 11:25", 1, listOf("cree"), listOf("cree", "confía", "espera", "obedezca")),
                FillVerseData(9, "Grande es el SEÑOR nuestro, y de mucha ___.", "Salmo 147:5", 1, listOf("potencia"), listOf("potencia", "gloria", "grandeza", "sabiduria")),
                FillVerseData(10, "Jehová torre de fortalezas, ___ de los justos.", "Salmo 112:8", 1, listOf("escudo"), listOf("escudo", "refugio", "protección", "fortaleza")),
                FillVerseData(11, "El que busca a Dios todas las buenas cosas ___ junto.", "Proverbios 8:17", 1, listOf("le vienen"), listOf("le vienen", "llegan", "son", "vienen")),
                FillVerseData(12, "Por la ___ seremos salvos.", "1 Pedro 4:18", 1, listOf("gracia"), listOf("gracia", "fe", "esperanza", "caridad")),
                FillVerseData(13, "Ahora ___ la fe, la esperanza y el amor; pero la mayor es el amor.", "1 Corintios 13:13", 1, listOf("permanece"), listOf("permanece", "queda", "existe", "está")),
                FillVerseData(14, "Sed prudentes y velad en vuestras ___ porque el enemigo anda al acecho.", "1 Pedro 5:8", 1, listOf("oraciones"), listOf("oraciones", "oración", "fe", "esperanza")),
                FillVerseData(15, "El que no ama no ha conocido a ___ porque Dios es amor.", "1 Juan 4:8", 1, listOf("Dios"), listOf("Dios", "Jesucristo", "el Padre", "el Señor"))
            )
        }
    }
}