package com.example.examenpantallas.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.examenpantallas.mvvm.HomeViewModel
import com.example.examenpantallas.mvvm.Jugador
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(auth: FirebaseAuth, onAddJugador: () -> Unit) {
    val vm: HomeViewModel = viewModel()
    val lista by vm.jugadores.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Plantilla temporada 25/26") }) },
        floatingActionButton = {
            Button(
                onClick = onAddJugador,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27D21F)),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
            ) { Text("Agregar jugador") }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { p ->
        LazyColumn(modifier = Modifier.padding(p).fillMaxSize().padding(bottom = 70.dp)) {
            items(lista) { j ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF2FCEE))
                ) {
                    Column {
                        AsyncImage(
                            model = j.imagen,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(210.dp),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(45.dp).clip(CircleShape).background(Color(0xFF27D21F)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(j.numero.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            
                            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                                Text(j.nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(j.nacionalidad, fontSize = 14.sp, color = Color.Gray)
                                Text(j.posicion, fontSize = 14.sp, color = Color.Gray)
                            }

                            IconButton(onClick = { vm.eliminar(j.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}