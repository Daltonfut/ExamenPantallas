package com.example.examenpantallas.mvvm

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginError: String? = null,
    val loginSuccess: Boolean = false
)

data class NuevoJugadorUiState(
    val nombre: String = "",
    val posicion: String = "",
    val numero: String = "",
    val nacionalidad: String = "",
    val imagenUrl: String = ""
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(e: String) { _uiState.update { it.copy(email = e) } }
    fun onPasswordChange(p: String) { _uiState.update { it.copy(password = p) } }
    fun clearLoginError() { _uiState.update { it.copy(loginError = null) } }

    fun login(auth: FirebaseAuth) {
        val email = _uiState.value.email
        val pass = _uiState.value.password
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(loginError = "Campos vacios") }
            return
        }
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener { _uiState.update { it.copy(loginSuccess = true) } }
            .addOnFailureListener { _uiState.update { it.copy(loginError = "Error al entrar") } }
    }
}

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val col = db.collection("Jugadores")
    private val _jugadores = MutableStateFlow<List<Jugador>>(emptyList())
    val jugadores: StateFlow<List<Jugador>> = _jugadores.asStateFlow()

    init {
        col.addSnapshotListener { snap, _ ->
            if (snap != null) {
                _jugadores.value = snap.documents.mapNotNull { d ->
                    d.toObject(Jugador::class.java)?.apply { id = d.id }
                }
            }
        }
    }

    fun eliminar(id: String) {
        if (id.isNotEmpty()) col.document(id).delete()
    }
}

class NuevoJugadorViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val col = db.collection("Jugadores")
    private val _uiState = MutableStateFlow(NuevoJugadorUiState())
    val uiState: StateFlow<NuevoJugadorUiState> = _uiState.asStateFlow()

    fun onNombreChange(v: String) { _uiState.update { it.copy(nombre = v) } }
    fun onPosicionChange(v: String) { _uiState.update { it.copy(posicion = v) } }
    fun onNumeroChange(v: String) { _uiState.update { it.copy(numero = v) } }
    fun onNacionalidadChange(v: String) { _uiState.update { it.copy(nacionalidad = v) } }
    fun onImagenUrlChange(v: String) { _uiState.update { it.copy(imagenUrl = v) } }

    fun add(onOk: () -> Unit) {
        val s = _uiState.value
        val j = Jugador(
            nombre = s.nombre,
            posicion = s.posicion,
            numero = s.numero.toIntOrNull() ?: 0,
            nacionalidad = s.nacionalidad,
            imagen = s.imagenUrl
        )
        col.add(j).addOnSuccessListener { onOk() }
    }
}