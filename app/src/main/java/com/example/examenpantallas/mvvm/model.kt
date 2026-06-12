package com.example.examenpantallas.mvvm

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class Jugador(
    @get:Exclude var id: String = "",
    @get:PropertyName("Nombre") @set:PropertyName("Nombre") var nombre: String = "",
    @get:PropertyName("Posición") @set:PropertyName("Posición") var posicion: String = "",
    @get:PropertyName("Número") @set:PropertyName("Número") var numero: Int = 0,
    @get:PropertyName("Nacionalidad") @set:PropertyName("Nacionalidad") var nacionalidad: String = "",
    @get:PropertyName("Imagen") @set:PropertyName("Imagen") var imagen: String = ""
)
