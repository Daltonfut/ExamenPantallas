package com.example.examenpantallas.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenpantallas.mvvm.NuevoJugadorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoJugador(onBack: () -> Unit) {
    val vm: NuevoJugadorViewModel = viewModel()
    val st by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nuevo jugador") })
        }
    ) { p ->
        Column(modifier = Modifier.padding(p).padding(16.dp)) {
            val mod = Modifier.fillMaxWidth().padding(vertical = 4.dp)

            OutlinedTextField(st.nombre, { vm.onNombreChange(it) }, label = { Text("Nombre") }, modifier = mod)
            OutlinedTextField(st.numero, { vm.onNumeroChange(it) }, label = { Text("Número") }, modifier = mod)
            OutlinedTextField(st.posicion, { vm.onPosicionChange(it) }, label = { Text("Posición") }, modifier = mod)
            OutlinedTextField(st.nacionalidad, { vm.onNacionalidadChange(it) }, label = { Text("Nacionalidad") }, modifier = mod)
            OutlinedTextField(st.imagenUrl, { vm.onImagenUrlChange(it) }, label = { Text("URL imagen") }, modifier = mod)

            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { vm.add(onBack) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27D21F)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar Jugador")
                }

                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}