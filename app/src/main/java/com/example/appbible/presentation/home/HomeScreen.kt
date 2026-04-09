package com.example.appbible.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appbible.presentation.theme.*
import java.time.LocalTime

@Composable
fun HomeScreen(
    onNavigateToLectura: () -> Unit,
    onNavigateToJuegos: () -> Unit,
    onNavigateToRetos: () -> Unit,
    onNavigateToProgreso: () -> Unit
) {
    val saludo = remember {
        val hour = LocalTime.now().hour
        when {
            hour < 12 -> "Buenos dias"
            hour < 18 -> "Buenas tardes"
            else -> "Buenas noches"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bible Learn",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MarronTexto
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = saludo,
            style = MaterialTheme.typography.titleLarge,
            color = MarronClaro
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onNavigateToLectura,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DoradoPrimario)
        ) {
            Text("Lectura Diaria")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onNavigateToJuegos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = VerdeEsperanza)
        ) {
            Text("Juegos")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onNavigateToRetos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Carmesi)
        ) {
            Text("Retos")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onNavigateToProgreso,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DoradoOscuro)
        ) {
            Text("Progreso")
        }
    }
}