package com.example.appbible.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appbible.presentation.theme.*

@Composable
fun HomeScreen(
    onNavigateToLectura: () -> Unit,
    onNavigateToJuegos: () -> Unit,
    onNavigateToRetos: () -> Unit,
    onNavigateToProgreso: () -> Unit,
    onNavigateToDailyQuestion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bible Learn",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MarronTexto,
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Aprende la Biblia jugando",
            style = MaterialTheme.typography.bodyLarge,
            color = MarronClaro,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MarronTexto),
            onClick = onNavigateToDailyQuestion
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pregunta del Día",
                    style = MaterialTheme.typography.titleLarge,
                    color = PergaminoFondo,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Toca aquí para comenzar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PergaminoClaro
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Versículo del Día",
                    style = MaterialTheme.typography.titleMedium,
                    color = MarronClaro
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "\"Porque tanto amó Dios al mundo, que dio a su Hijo unigénito, para que todo aquel que en él cree no se pierda, sino que tenga vida eterna.\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MarronTexto,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Juan 3:16",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DoradoPrimario,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
