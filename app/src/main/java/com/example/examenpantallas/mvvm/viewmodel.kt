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

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun clearLoginError() {
        _uiState.update { it.copy(loginError = null) }
    }

    fun login(auth: FirebaseAuth) {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(loginError = "El email y la contraseña no pueden estar vacíos.") }
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.update { it.copy(loginSuccess = true) }
            }
            .addOnFailureListener { e ->
                _uiState.update { it.copy(loginError = e.message ?: "Fallo de autenticación.") }
            }
    }
}

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val jugadoresCollection = db.collection("Jugadores")

    private val _jugadores = MutableStateFlow<List<Jugador>>(emptyList())
    val jugadores: StateFlow<List<Jugador>> = _jugadores.asStateFlow()

    init {
        obtenerJugadores()
    }

    private fun obtenerJugadores() {
        jugadoresCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val jugadoresList = snapshot.documents.mapNotNull { doc ->
                    val jugador = doc.toObject(Jugador::class.java)
                    jugador?.id = doc.id
                    jugador
                }
                _jugadores.value = jugadoresList
            }
        }
    }
}
class NuevoJugadorViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val jugadoresCollection = db.collection("Jugadores")

    private val _uiState = MutableStateFlow(NuevoJugadorUiState())
    val uiState: StateFlow<NuevoJugadorUiState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) { _uiState.update { it.copy(nombre = nombre) } }
    fun onPosicionChange(posicion: String) { _uiState.update { it.copy(posicion = posicion) } }
    fun onNumeroChange(numero: String) { _uiState.update { it.copy(numero = numero) } }
    fun onNacionalidadChange(nacionalidad: String) { _uiState.update { it.copy(nacionalidad = nacionalidad) } }
    fun onImagenUrlChange(imagenUrl: String) { _uiState.update { it.copy(imagenUrl = imagenUrl) } }

    fun addJugador(onSuccess: () -> Unit) {
        val uiStateValue = _uiState.value
        val nuevoJugador = Jugador(
            nombre = uiStateValue.nombre,
            posicion = uiStateValue.posicion,
            numero = uiStateValue.numero,
            nacionalidad = uiStateValue.nacionalidad,
            URL = uiStateValue.imagenUrl
        )

        jugadoresCollection.add(nuevoJugador)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
            }
    }
}