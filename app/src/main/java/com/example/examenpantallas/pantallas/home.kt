package com.example.examenpantallas.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.examenpantallas.mvvm.HomeViewModel
import com.example.examenpantallas.mvvm.Jugador
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(auth: FirebaseAuth, onAddJugador: () -> Unit) {
    val viewModel: HomeViewModel = viewModel()
    val jugadores by viewModel.jugadores.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Jugadores") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddJugador() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir jugador")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(jugadores) { jugador ->
                JugadorItem(jugador = jugador)
            }
        }
    }
}

@Composable
fun JugadorItem(jugador: Jugador) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            if (jugador.URL.isNotEmpty()) {
                AsyncImage(
                    model = jugador.URL,
                    contentDescription = "Imagen de ${jugador.nombre}",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(modifier = Modifier.weight(1.1f)) {
                Text(text = "Nombre: ${jugador.nombre}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Posición: ${jugador.posicion}")
                Text(text = "Número: ${jugador.numero}")
                Text(text = "Nacionalidad: ${jugador.nacionalidad}")
            }
        }
    }
}
