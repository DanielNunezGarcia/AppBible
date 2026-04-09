package com.example.appbible.presentation.lectura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appbible.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LecturaScreen(
    onBack: () -> Unit,
    viewModel: LecturaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📖 Lectura Diaria", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Racha: ${uiState.rachaDias} días",
                style = MaterialTheme.typography.titleMedium,
                color = DoradoPrimario
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PergaminoClaro)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.referencia,
                        style = MaterialTheme.typography.titleLarge,
                        color = Carmesi,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = uiState.versiculo,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic
                        ),
                        color = MarronTexto,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = VerdeEsperanza.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💭 Reflexión del día",
                        style = MaterialTheme.typography.titleMedium,
                        color = VerdeEsperanza,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = uiState.reflexion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronTexto
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box {
                TextButton(onClick = { expanded = true }) {
                    Text("📖 Versión: ${uiState.version}")
                }
                
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf("RVR1960", "NTV", "NVI").forEach { version ->
                        DropdownMenuItem(
                            text = { Text(version) },
                            onClick = {
                                viewModel.cambiarVersion(version)
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { viewModel.marcarLeido() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.yaLeido,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.yaLeido) VerdeClaro else DoradoPrimario
                )
            ) {
                Text(if (uiState.yaLeido) "✓ Leído" else "✅ Marcar como leído (+50 XP)")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            TextButton(onClick = { viewModel.siguienteLectura() }) {
                Text("➡️ Siguiente")
            }
        }
    }
}