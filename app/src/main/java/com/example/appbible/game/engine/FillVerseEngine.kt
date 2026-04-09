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
        val currentVerse = state.verses[state.currentIndex]
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
}
