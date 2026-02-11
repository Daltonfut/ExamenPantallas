package com.example.examenpantallas.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenpantallas.mvvm.NuevoJugadorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoJugador(onBack: () -> Unit) {
    val viewModel: NuevoJugadorViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Jugador") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.posicion,
                onValueChange = { viewModel.onPosicionChange(it) },
                label = { Text("Posición") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.numero,
                onValueChange = { viewModel.onNumeroChange(it) },
                label = { Text("Número") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.nacionalidad,
                onValueChange = { viewModel.onNacionalidadChange(it) },
                label = { Text("Nacionalidad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.imagenUrl,
                onValueChange = { viewModel.onImagenUrlChange(it) },
                label = { Text("URL de Imagen") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.addJugador(onSuccess = onBack)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar jugador")
            }
        }
    }
}