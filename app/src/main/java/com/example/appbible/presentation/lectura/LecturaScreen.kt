package com.example.appbible.presentation.lectura

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appbible.presentation.theme.*

@Composable
fun LecturaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bible Learn",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MarronTexto
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Genesis 1:1",
                    style = MaterialTheme.typography.titleLarge,
                    color = Carmesi,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "\"En el principio creo Dios los cielos y la tierra.\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MarronTexto
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Reflexion del dia",
                    style = MaterialTheme.typography.titleMedium,
                    color = VerdeEsperanza,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "En el principio de todo, Dios creo el universo con amor. Cada dia es una nueva oportunidad.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MarronTexto
                )
            }
        }
    }
}