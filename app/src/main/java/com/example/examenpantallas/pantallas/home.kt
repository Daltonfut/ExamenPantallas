package com.example.examenpantallas.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenpantallas.mvvm.HomeViewModel
import com.example.examenpantallas.mvvm.Jugador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onAddJugador: () -> Unit) {
    val viewModel: HomeViewModel = viewModel()
    val jugadores by viewModel.jugadores

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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${jugador.nombre}")
            Text(text = "Posición: ${jugador.posicion}")
            Text(text = "Número: ${jugador.numero}")
        }
    }
}
