package com.example.appbible.presentation.dailyquestion

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appbible.game.content.DailyQuestion
import com.example.appbible.game.content.getDailyQuestions
import com.example.appbible.presentation.theme.*
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyQuestionScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("app_bible_prefs", Context.MODE_PRIVATE)
    
    val calendar = Calendar.getInstance()
    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
    val year = calendar.get(Calendar.YEAR)
    val uniqueDay = "$year-$dayOfYear"
    
    val savedDay = sharedPrefs.getString("daily_question_day", "")
    var currentQuestion by remember { mutableStateOf<DailyQuestion?>(null) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        val savedId = sharedPrefs.getInt("daily_question_id", -1)
        val allQuestions = getDailyQuestions()
        
        if (savedDay == uniqueDay && savedId != -1) {
            currentQuestion = allQuestions.find { it.id == savedId }
        }
        
        if (currentQuestion == null) {
            currentQuestion = allQuestions.random()
            sharedPrefs.edit()
                .putString("daily_question_day", uniqueDay)
                .putInt("daily_question_id", currentQuestion?.id ?: -1)
                .apply()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Pregunta del Día", 
                        fontWeight = FontWeight.Bold 
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PergaminoClaro,
                    titleContentColor = MarronTexto
                )
            )
        },
        containerColor = PergaminoFondo
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currentQuestion?.let { question ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = question.question,
                            style = MaterialTheme.typography.titleLarge,
                            color = MarronTexto,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (!showResult) {
                    question.options.forEach { option ->
                        Button(
                            onClick = {
                                selectedAnswer = option
                                showResult = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MarronTexto
                            )
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = PergaminoFondo
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                } else {
                    val isCorrect = selectedAnswer == question.correctAnswer
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) VerdeEsperanza.copy(alpha = 0.2f) else Carmesi.copy(alpha = 0.2f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isCorrect) "✅ ¡Correcto!" else "❌ Incorrecto",
                                style = MaterialTheme.typography.titleLarge,
                                color = if (isCorrect) VerdeEsperanza else Carmesi,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Respuesta correcta:",
                                style = MaterialTheme.typography.titleMedium,
                                color = MarronClaro,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = question.correctAnswer,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MarronTexto,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            HorizontalDivider(
                                color = MarronClaro.copy(alpha = 0.3f),
                                thickness = 1.dp
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Text(
                                text = "Versículo Relacionado",
                                style = MaterialTheme.typography.titleMedium,
                                color = DoradoPrimario,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = PergaminoClaro.copy(alpha = 0.5f)
                                )
                            ) {
                                Text(
                                    text = question.relatedVerses.first(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MarronTexto,
                                    modifier = Modifier.padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Más versículos:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MarronClaro,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            question.relatedVerses.drop(1).forEach { verse ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = PergaminoClaro.copy(alpha = 0.3f)
                                    )
                                ) {
                                    Text(
                                        text = verse,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MarronTexto,
                                        modifier = Modifier.padding(12.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            HorizontalDivider(
                                color = MarronClaro.copy(alpha = 0.3f),
                                thickness = 1.dp
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = question.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MarronClaro,
                                textAlign = TextAlign.Center,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            } ?: run {
                Spacer(modifier = Modifier.height(100.dp))
                CircularProgressIndicator(color = DoradoPrimario)
            }
        }
    }
}
