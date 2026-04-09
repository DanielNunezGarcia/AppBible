package com.example.appbible.game.engine

import com.example.appbible.game.model.FlashCard
import com.example.appbible.game.model.GameResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemorizeEngine @Inject constructor() {

    private val _currentState = MutableStateFlow(MemorizeState())
    val currentState: StateFlow<MemorizeState> = _currentState.asStateFlow()

    private var allFlashCards: List<FlashCard> = emptyList()
    private var currentCards: List<FlashCard> = emptyList()

    fun loadFlashCards(difficulty: String = "facil") {
        allFlashCards = getFlashCards()
        currentCards = allFlashCards.shuffled().take(5)

        _currentState.value = MemorizeState(
            flashCards = currentCards,
            currentIndex = 0,
            currentLevel = 1,
            score = 0,
            correctAnswers = 0,
            wrongAnswers = 0,
            isGameOver = false,
            userAnswer = "",
            revealedWords = emptyList()
        )
    }

    fun revealNextLevel() {
        val state = _currentState.value
        val currentCard = state.flashCards[state.currentIndex]
        
        val newRevealedCount = when (state.currentLevel) {
            1 -> 2
            2 -> 5
            3 -> 10
            4 -> currentCard.allWords.size - 1
            else -> currentCard.allWords.size
        }

        _currentState.value = state.copy(
            currentLevel = state.currentLevel + 1,
            revealedWords = currentCard.allWords.take(newRevealedCount)
        )
    }

    fun setUserAnswer(answer: String) {
        _currentState.value = _currentState.value.copy(userAnswer = answer)
    }

    fun checkAnswer(): Boolean {
        val state = _currentState.value
        val currentCard = state.flashCards[state.currentIndex]
        val isCorrect = state.userAnswer.trim()
            .equals(currentCard.verseText, ignoreCase = true)

        val newScore = if (isCorrect) {
            state.score + calculateScore(state.currentLevel)
        } else {
            state.score
        }

        _currentState.value = state.copy(
            isAnswered = true,
            isCorrect = isCorrect,
            score = newScore,
            correctAnswers = if (isCorrect) state.correctAnswers + 1 else state.correctAnswers,
            wrongAnswers = if (!isCorrect) state.wrongAnswers + 1 else state.wrongAnswers
        )

        return isCorrect
    }

    fun nextCard() {
        val state = _currentState.value
        if (state.currentIndex >= state.flashCards.size - 1) {
            _currentState.value = state.copy(isGameOver = true)
            return
        }

        _currentState.value = state.copy(
            currentIndex = state.currentIndex + 1,
            currentLevel = 1,
            userAnswer = "",
            isAnswered = false,
            isCorrect = null,
            revealedWords = emptyList()
        )
    }

    fun finishGame(): GameResult {
        val state = _currentState.value
        return GameResult(
            gameType = "memorize",
            score = state.score,
            totalQuestions = state.flashCards.size,
            correctAnswers = state.correctAnswers,
            wrongAnswers = state.wrongAnswers,
            xpEarned = state.score / 5 + (state.correctAnswers * 20),
            pointsEarned = state.score,
            durationSeconds = 0,
            newAchievements = emptyList()
        )
    }

    private fun calculateScore(level: Int): Int {
        // Más puntos si completa con menos pistas reveladas
        return when (level) {
            1 -> 500 // Sin pistas
            2 -> 400 // 2 palabras reveladas
            3 -> 300 // 5 palabras
            4 -> 200 // 10 palabras
            5 -> 100 // Todas menos 1
            else -> 50
        }
    }

    companion object {
        private fun getFlashCards(): List<FlashCard> {
            return listOf(
                FlashCard(1, "Génesis 1:1", "En el principio creó Dios los cielos y la tierra.", listOf("Génesis", "principio", "creó", "Dios", "cielos", "tierra"), "creacion"),
                FlashCard(2, "Juan 3:16", "De tanto amó Dios al mundo, que dio a su Hijo unigénito, para que todo el que cree en él no se pierda, sino que tenga vida eterna.", listOf("Juan", "Dios", "amó", "mundo", "dio", "Hijo", "unigénito", "cree", "pierda", "vida", "eterna"), "salvacion"),
                FlashCard(3, "Salmo 23:1", "Jehová es mi pastor; nada me faltará.", listOf("Salmo", "Jehová", "pastor", "nada", "faltará"), "proteccion"),
                FlashCard(4, "Salmo 119:105", "Lámpara es a mis pies tu palabra, y lumbrera a mi camino.", listOf("Salmo", "lámpara", "pies", "palabra", "lumbrera", "camino"), "orientacion"),
                FlashCard(5, "Proverbios 3:5", "Confía en Jehovah de todo tu corazón, y no te apoyes en tu propio entendimiento.", listOf("Proverbios", "confía", "Jehovah", "corazón", "apoyes", "entendimiento"), "confianza"),
                FlashCard(6, "Isaías 40:31", "Pero los que esperan a Jehovah tendrán nuevas fuerzas; levantarán alas como las águilas; correrán, y no se cansarán; caminarán, y no se fatigarán.", listOf("Isaías", "esperan", "Jehovah", "nuevas", "fuerzas", "alas", "águilas", "correrán", "cansarán", "caminarán", "fatigarán"), "esperanza"),
                FlashCard(7, "Romanos 8:28", "Y sabemos que todas las cosas cooperan para el bien de los que aman a Dios, de los que son llamados según su propósito.", listOf("Romanos", "todas", "cosas", "cooperan", "bien", "aman", "Dios", "llamados", "propósito"), "providencia"),
                FlashCard(8, "Filipenses 4:13", "Todo lo puedo en Cristo que me fortalece.", listOf("Filipenses", "puedo", "Cristo", "fortalece"), "fortaleza"),
                FlashCard(9, "Efesios 2:8", "Porque por gracia habéis sido salvos por la fe; y esto no viene de vosotros, sino que es don de Dios.", listOf("Efesios", "gracia", "salvos", "fe", "don", "Dios"), "gracia"),
                FlashCard(10, "Hebreos 11:1", "Es la certeza de lo que se espera y la convicción de lo que no se ve.", listOf("Hebreos", "certeza", "espera", "convicción"), "fe")
            )
        }
    }

    data class MemorizeState(
        val flashCards: List<FlashCard> = emptyList(),
        val currentIndex: Int = 0,
        val currentLevel: Int = 1, // 1-5 (5 niveles de dificultad progresiva)
        val score: Int = 0,
        val correctAnswers: Int = 0,
        val wrongAnswers: Int = 0,
        val isGameOver: Boolean = false,
        val userAnswer: String = "",
        val revealedWords: List<String> = emptyList(),
        val isAnswered: Boolean = false,
        val isCorrect: Boolean? = null
    ) {
        val currentCard: FlashCard? get() = flashCards.getOrNull(currentIndex)
        val totalLevels: Int = 5
    }
}
