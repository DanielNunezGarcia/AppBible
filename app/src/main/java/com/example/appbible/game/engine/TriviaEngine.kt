package com.example.appbible.game.engine

import com.example.appbible.game.content.getTriviaQuestions
import com.example.appbible.game.model.GameResult
import com.example.appbible.game.model.TriviaQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TriviaEngine @Inject constructor() {

    private val _currentState = MutableStateFlow(TriviaState())
    val currentState: StateFlow<TriviaState> = _currentState.asStateFlow()

    private var allQuestions: List<TriviaQuestion> = emptyList()
    private var currentQuestions: List<TriviaQuestion> = emptyList()

    fun loadQuestions(difficulty: String = "facil", category: String = "todos") {
        allQuestions = getTriviaQuestions()
        currentQuestions = allQuestions.filter {
            (category == "todos" || it.category == category) && it.difficulty == difficulty
        }.shuffled().take(10)
        
        _currentState.value = TriviaState(
            questions = currentQuestions,
            currentIndex = 0,
            score = 0,
            correctAnswers = 0,
            wrongAnswers = 0,
            lives = 3,
            timeRemaining = 30,
            isGameOver = false
        )
    }

    fun selectAnswer(answerIndex: Int) {
        val state = _currentState.value
        if (state.isAnswered || state.isGameOver) return

        val currentQuestion = state.questions[state.currentIndex]
        val isCorrect = answerIndex == currentQuestion.correctIndex
        
        val newScore = if (isCorrect) {
            state.score + calculateScore(state.timeRemaining, state.difficulty)
        } else {
            state.score
        }

        _currentState.value = state.copy(
            selectedAnswer = answerIndex,
            isAnswered = true,
            isCorrect = isCorrect,
            score = newScore,
            correctAnswers = if (isCorrect) state.correctAnswers + 1 else state.correctAnswers,
            wrongAnswers = if (!isCorrect) state.wrongAnswers + 1 else state.wrongAnswers,
            lives = if (!isCorrect) state.lives - 1 else state.lives
        )
    }

    fun nextQuestion() {
        val state = _currentState.value
        if (state.currentIndex >= state.questions.size - 1) {
            _currentState.value = state.copy(isGameOver = true)
            return
        }

        _currentState.value = state.copy(
            currentIndex = state.currentIndex + 1,
            selectedAnswer = null,
            isAnswered = false,
            isCorrect = null,
            timeRemaining = getTimeForDifficulty(state.difficulty)
        )
    }

    fun decrementTime() {
        val state = _currentState.value
        if (state.timeRemaining > 0 && !state.isAnswered && !state.isGameOver) {
            _currentState.value = state.copy(
                timeRemaining = state.timeRemaining - 1
            )
            if (state.timeRemaining == 1) {
                selectAnswer(-1) // Time out - wrong answer
            }
        }
    }

    fun finishGame(): GameResult {
        val state = _currentState.value
        return GameResult(
            gameType = "trivia",
            score = state.score,
            totalQuestions = state.questions.size,
            correctAnswers = state.correctAnswers,
            wrongAnswers = state.wrongAnswers,
            xpEarned = calculateXP(state.score, state.correctAnswers),
            pointsEarned = state.score,
            durationSeconds = 0, // TODO: Track actual duration
            newAchievements = emptyList() // TODO: Check achievements
        )
    }

    private fun calculateScore(timeRemaining: Int, difficulty: String): Int {
        val baseScore = 100
        val timeBonus = timeRemaining * 2
        val difficultyMultiplier: Double = when (difficulty) {
            "dificil" -> 2.0
            "medio" -> 1.5
            else -> 1.0
        }
        return ((baseScore + timeBonus) * difficultyMultiplier).toInt()
    }

    private fun calculateXP(score: Int, correctAnswers: Int): Int {
        return score / 10 + (correctAnswers * 5)
    }

    private fun getTimeForDifficulty(difficulty: String): Int {
        return when (difficulty) {
            "dificil" -> 15
            "medio" -> 20
            else -> 30
        }
    }

    data class TriviaState(
        val questions: List<TriviaQuestion> = emptyList(),
        val currentIndex: Int = 0,
        val score: Int = 0,
        val correctAnswers: Int = 0,
        val wrongAnswers: Int = 0,
        val lives: Int = 3,
        val timeRemaining: Int = 30,
        val selectedAnswer: Int? = null,
        val isAnswered: Boolean = false,
        val isCorrect: Boolean? = null,
        val isGameOver: Boolean = false,
        val difficulty: String = "facil"
    ) {
        val currentQuestion: TriviaQuestion? get() = questions.getOrNull(currentIndex)
    }
}
