package com.example.appbible.presentation.retos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appbible.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetosScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Bible Learn",
                        fontWeight = FontWeight.Bold
                    ) 
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🔥",
                style = MaterialTheme.typography.displayLarge
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Próximamente",
                style = MaterialTheme.typography.headlineMedium,
                color = MarronTexto,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Estamos preparando emocionantes retos diarios para ti:",
                style = MaterialTheme.typography.bodyLarge,
                color = MarronClaro,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    RetoItem("📅", "Reto de 7 días", "Completa una lectura cada día")
                    RetoItem("🙏", "Oración guiada", "Sigue un plan de oración semanal")
                    RetoItem("🙏", "Agradecimiento diario", "Escribe 3 cosas por las que estás agradecido")
                    RetoItem("📖", "Versículo del día", "Memoriza un versículo nuevo cada día")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.1f))
            ) {
                Text(
                    text = "\"Porque delays no son negativas, son oportunidades de crecimiento espiritual.\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VerdeEsperanza,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun RetoItem(emoji: String, titulo: String, descripcion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MarronTexto,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MarronClaro
            )
        }
    }
}